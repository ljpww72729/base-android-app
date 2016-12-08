package com.ww.lp.base.modules.main.home;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.orhanobut.logger.Logger;
import com.ww.lp.base.data.CarouselInfo;
import com.ww.lp.base.data.ProjectInfo;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.network.ServerInterface;
import com.ww.lp.base.utils.ToastUtils;
import com.ww.lp.base.utils.schedulers.BaseSchedulerProvider;

import java.util.ArrayList;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class HomePresenter implements HomeContract.Presenter {

    @NonNull
    private String requestTag;

    @NonNull
    private final ServerImp mServerImp;
    @NonNull
    private final HomeContract.View mContractView;
    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;
    @NonNull
    private CompositeSubscription mSubscriptions;

    public HomePresenter(@NonNull String requestTag, @NonNull ServerImp serverImp,
                         @NonNull HomeContract.View contractView,
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
        loadCarouselImgList();
        loadProjectList();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    /**
     * 请求轮播图列表
     */
    private void loadCarouselImgList() {
        Subscription subscription = mServerImp
                .commonSingle(requestTag, Request.Method.GET, ServerInterface.carousel, null, CarouselInfo[].class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<CarouselInfo[]>() {
                    @Override
                    public void onSuccess(CarouselInfo[] carouselInfoList) {
//                        mView.removeProgressDialog();
                        //请求成功
//                        mView.success(loginResult);
                        ArrayList<CarouselInfo> arrayList = new ArrayList<CarouselInfo>();
                        for (int i = 0; i < carouselInfoList.length; i++){
                            arrayList.add(carouselInfoList[i]);
                        }
                        mContractView.updateCarouselView(arrayList);


                    }

                    @Override
                    public void onError(Throwable error) {
//                        mView.removeProgressDialog();
                        ToastUtils.toastError(error);
                        Logger.d(error);
                    }
                });
        mSubscriptions.add(subscription);

    }
    /**
     * 请求项目列表
     */
    private void loadProjectList() {
        Subscription subscription = mServerImp
                .commonSingle(requestTag, Request.Method.GET, ServerInterface.project_list, null, ProjectInfo[].class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<ProjectInfo[]>() {
                    @Override
                    public void onSuccess(ProjectInfo[] projectInfoList) {
//                        mView.removeProgressDialog();
                        //请求成功
//                        mView.success(loginResult);
                        ArrayList<ProjectInfo> arrayList = new ArrayList<ProjectInfo>();
                        for (int i = 0; i < projectInfoList.length; i++){
                            arrayList.add(projectInfoList[i]);
                        }
                        mContractView.updateProjectList(arrayList);


                    }

                    @Override
                    public void onError(Throwable error) {
//                        mView.removeProgressDialog();
                        ToastUtils.toastError(error);
                        Logger.d(error);
                    }
                });
        mSubscriptions.add(subscription);

    }


}
