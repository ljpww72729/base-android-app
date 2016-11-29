package com.ww.lp.base.modules.team.list;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.orhanobut.logger.Logger;
import com.ww.lp.base.data.TeamResult;
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
        loadTeamList();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    /**
     * 请求项目列表
     */
    private void loadTeamList() {
        Subscription subscription = mServerImp
                .commonSingle(requestTag, Request.Method.GET, ServerInterface.team_list, null, TeamResult[].class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<TeamResult[]>() {
                    @Override
                    public void onSuccess(TeamResult[] teamList) {
//                        mView.removeProgressDialog();
                        //请求成功
//                        mView.success(loginResult);
                        ArrayList<TeamResult> arrayList = new ArrayList<TeamResult>();
                        for (int i = 0; i < teamList.length; i++){
                            arrayList.add(teamList[i]);
                        }
                        mContractView.updateTeamList(arrayList);


                    }

                    @Override
                    public void onError(Throwable error) {
//                        mView.removeProgressDialog();
                        ToastUtils.toastShort(error.getCause().getMessage());
                        Logger.d(error.getCause().getMessage());
                    }
                });
        mSubscriptions.add(subscription);

    }

}
