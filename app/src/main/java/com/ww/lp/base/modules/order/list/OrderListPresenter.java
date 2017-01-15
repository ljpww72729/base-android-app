package com.ww.lp.base.modules.order.list;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.orhanobut.logger.Logger;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.data.project.ProjectInfo;
import com.ww.lp.base.data.project.ProjectListResult;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.network.ServerInterface;
import com.ww.lp.base.utils.SPUtils;
import com.ww.lp.base.utils.ToastUtils;
import com.ww.lp.base.utils.schedulers.BaseSchedulerProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class OrderListPresenter implements OrderListContract.Presenter {

    @NonNull
    private String requestTag;

    @NonNull
    private final ServerImp mServerImp;
    @NonNull
    private final OrderListContract.View mContractView;
    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;
    @NonNull
    private CompositeSubscription mSubscriptions;

    public OrderListPresenter(@NonNull String requestTag, @NonNull ServerImp serverImp,
                              @NonNull OrderListContract.View contractView,
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
    /**
     * 请求项目列表
     */
    @Override
    public void loadProjectList(String order_flag, String isOnlyQueryMyPublis, int pageIndex) {
        String requestUrl = ServerInterface.project_list;
        Map<String, String> params = new HashMap<>();
        if (order_flag.equals(OrderListActivity.ACCEPT)){
            requestUrl = ServerInterface.team_project_list;
            params.put("teamId", (String)SPUtils.get(CustomApplication.self(), SPUtils.TEAM_ID, ""));
        }else{
            params.put("isOnlyQueryMyPublish", isOnlyQueryMyPublis);
        }
        params.put("pageIndex", pageIndex + "");
        params.put("token", (String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""));
        Subscription subscription = mServerImp
                .commonSingle(requestTag, Request.Method.POST, requestUrl, params, ProjectListResult.class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<ProjectListResult>() {
                    @Override
                    public void onSuccess(ProjectListResult projectList) {
                        ArrayList<ProjectInfo> arrayList = projectList.getData().getList();
                        mContractView.updateOrderList(true, arrayList, projectList.getData().getPageCount());
                    }

                    @Override
                    public void onError(Throwable error) {
//                        mView.removeProgressDialog();
                        mContractView.updateOrderList(false, new ArrayList<ProjectInfo>(), 0);
                        ToastUtils.toastError(error);
                        Logger.d(error);
                    }
                });
        mSubscriptions.add(subscription);

    }
}
