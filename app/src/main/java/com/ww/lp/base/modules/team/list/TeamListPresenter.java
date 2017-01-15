package com.ww.lp.base.modules.team.list;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.orhanobut.logger.Logger;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.data.team.TeamInfo;
import com.ww.lp.base.data.team.TeamListResult;
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

public class TeamListPresenter implements TeamListContract.Presenter {

    @NonNull
    private String requestTag;

    @NonNull
    private final ServerImp mServerImp;
    @NonNull
    private final TeamListContract.View mContractView;
    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;
    @NonNull
    private CompositeSubscription mSubscriptions;

    public TeamListPresenter(@NonNull String requestTag, @NonNull ServerImp serverImp,
                             @NonNull TeamListContract.View contractView,
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
     * 请求团队列表
     * @param pageNum
     */
    @Override
    public void loadTeamList(int pageNum) {
        Map<String, String> params = new HashMap<>();
        params.put("token", (String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""));
        params.put("isOnlyQueryMyOwn", "0");
        Subscription subscription = mServerImp
                .commonSingle(requestTag, Request.Method.POST, ServerInterface.team_list, params, TeamListResult.class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<TeamListResult>() {
                    @Override
                    public void onSuccess(TeamListResult teamList) {
                        mContractView.updateTeamList(true, teamList.getData().getList(), teamList.getData().getPageCount());
                    }

                    @Override
                    public void onError(Throwable error) {
                        mContractView.updateTeamList(false, new ArrayList<TeamInfo>(), 0);
                        ToastUtils.toastError(error);
                        Logger.d(error);
                    }
                });
        mSubscriptions.add(subscription);

    }

}
