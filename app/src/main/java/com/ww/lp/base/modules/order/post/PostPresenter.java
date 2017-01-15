package com.ww.lp.base.modules.order.post;

import com.google.gson.Gson;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.orhanobut.logger.Logger;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.components.volleymw.DataPart;
import com.ww.lp.base.data.CommonResult;
import com.ww.lp.base.data.UploadResult;
import com.ww.lp.base.data.project.ProjectInfo;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.network.ServerInterface;
import com.ww.lp.base.utils.Constants;
import com.ww.lp.base.utils.SPUtils;
import com.ww.lp.base.utils.ToastUtils;
import com.ww.lp.base.utils.schedulers.BaseSchedulerProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.SingleSubscriber;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class PostPresenter implements PostContract.Presenter {

    @NonNull
    private String requestTag;

    @NonNull
    private final ServerImp mServerImp;
    @NonNull
    private final PostContract.View mContractView;
    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;
    @NonNull
    private CompositeSubscription mSubscriptions;
    private ArrayList<String> uploadImgUrlList = new ArrayList<>();

    public PostPresenter(@NonNull String requestTag, @NonNull ServerImp serverImp,
                         @NonNull PostContract.View contractView,
                         @NonNull BaseSchedulerProvider schedulerProvider) {
        this.requestTag = requestTag;
        mServerImp = checkNotNull(serverImp, "serverImp cannot be null!");
        mContractView = checkNotNull(contractView, "loginView cannot be null!");
        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeSubscription();
        mContractView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        //此处为页面打开后开始加载数据时调用的方法
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void post(ProjectInfo projectPostInfo, final boolean isAdd) {
        Map<String, String> params = new HashMap<>();
        String requestUrl = ServerInterface.project_publish;
        if (!isAdd){
            requestUrl = ServerInterface.project_edit;
            params.put("projectId", projectPostInfo.getProjectId());
            params.put("status", projectPostInfo.getStatus() + "");
        }
        // TODO: 16/11/29 此处title拼写错误
        params.put("tittle", projectPostInfo.getTittle());
        params.put("describes", projectPostInfo.getDescribes());
        params.put("price", projectPostInfo.getPrice());
        params.put("phoneNum", projectPostInfo.getPhoneNum());
        String img = "";
        if (projectPostInfo.getProjectImgs() != null && projectPostInfo.getProjectImgs().size() > 0){
            img = projectPostInfo.getProjectImgs().get(0).getImg();
        }
        params.put("img", img);
        params.put(Constants.PROJECTIMGS, new Gson().toJson(projectPostInfo.getProjectImgs()));
        params.put("token", (String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""));
        Subscription subscription = mServerImp
                .commonSingle(requestTag, Request.Method.POST, requestUrl, params, CommonResult.class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<CommonResult>() {
                    @Override
                    public void onSuccess(CommonResult commonResult) {
//                        mView.removeProgressDialog();
                        //请求成功
//                        mView.success(loginResult);
//                        mContractView.updateOrderList(arrayList);
                        mContractView.addOrModifySuccess(commonResult.isData());
                    }

                    @Override
                    public void onError(Throwable error) {
//                        mView.removeProgressDialog();
                        mContractView.addOrModifySuccess(false);
                        ToastUtils.toastError(error);
                        Logger.d("onError");
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void uploadFile(final ArrayList<String> arrayList) {
        Map<String, String> params = new HashMap<>();
        params.put("token", (String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""));
        Map<String, DataPart> fileParams = new HashMap<>();
        for (int i = 0; i < arrayList.size(); i++) {
            File file = new File(arrayList.get(i));
            fileParams.put("img" + i, new DataPart(file.getName(), file.getAbsolutePath(), "image/jpeg"));
        }
        Subscription subscription = mServerImp.uploadFile(requestTag, ServerInterface.uploadFile, params, fileParams, UploadResult.class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Subscriber<UploadResult>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted");
                        mContractView.uploadFileResult(true, uploadImgUrlList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mContractView.uploadFileResult(false, new ArrayList<String>());
                        ToastUtils.toastError(e);
                        Logger.d("onError");
                    }

                    @Override
                    public void onNext(UploadResult uploadResult) {
                        uploadImgUrlList.add(uploadResult.getData());
                        Logger.d(uploadResult.getData());
                    }
                });
        mSubscriptions.add(subscription);

    }


}
