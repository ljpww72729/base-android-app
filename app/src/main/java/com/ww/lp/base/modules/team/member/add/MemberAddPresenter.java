package com.ww.lp.base.modules.team.member.add;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.orhanobut.logger.Logger;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.components.volleymw.DataPart;
import com.ww.lp.base.data.CommonResult;
import com.ww.lp.base.data.UploadResult;
import com.ww.lp.base.data.team.TeamMember;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.network.ServerInterface;
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

public class MemberAddPresenter implements MemberAddContract.Presenter {

    @NonNull
    private String requestTag;

    @NonNull
    private final ServerImp mServerImp;
    @NonNull
    private final MemberAddContract.View mContractView;
    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;
    @NonNull
    private CompositeSubscription mSubscriptions;

    private String uploadImgUrl;

    public MemberAddPresenter(@NonNull String requestTag, @NonNull ServerImp serverImp,
                              @NonNull MemberAddContract.View contractView,
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
                        mContractView.uploadFileResult(true, uploadImgUrl);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mContractView.uploadFileResult(false, "");
                        ToastUtils.toastError(e);
                        Logger.d("onError");
                    }

                    @Override
                    public void onNext(UploadResult uploadResult) {
                        uploadImgUrl = uploadResult.getData();
                        Logger.d(uploadResult.getData());
                    }
                });
        mSubscriptions.add(subscription);

    }

    @Override
    public void complete(TeamMember teamMember, boolean isAdd) {
        Map<String, String> params = new HashMap<>();
        String requestUrl = ServerInterface.member_add;
        if (!isAdd){
            requestUrl = ServerInterface.member_edit;
            params.put("memberId", teamMember.getMemberId());
        }

        params.put("teamId", (String) SPUtils.get(CustomApplication.self(), SPUtils.TEAM_ID, ""));
        params.put("position", teamMember.getPosition());
        params.put("yearNum", teamMember.getYearNum());
        params.put("img", teamMember.getImg());
        params.put("teamAdmin", "0");
        params.put("username", teamMember.getUsername());
        params.put("introduction", teamMember.getIntroduction());
        params.put("token", (String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""));
        Subscription subscription = mServerImp
                .commonSingle(requestTag, Request.Method.POST, requestUrl, params, CommonResult.class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<CommonResult>() {
                    @Override
                    public void onSuccess(CommonResult commonResult) {
                        mContractView.completeResult(true);
                    }

                    @Override
                    public void onError(Throwable error) {
                        mContractView.completeResult(false);
                        ToastUtils.toastError(error);
                        Logger.d("onError");
                    }
                });
        mSubscriptions.add(subscription);
    }

}
