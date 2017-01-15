package com.ww.lp.base.modules.team.member;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.orhanobut.logger.Logger;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.data.BaseResult;
import com.ww.lp.base.data.team.TeamMember;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.network.ServerInterface;
import com.ww.lp.base.utils.SPUtils;
import com.ww.lp.base.utils.ToastUtils;
import com.ww.lp.base.utils.schedulers.BaseSchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class MemberPresenter implements MemberContract.Presenter {

    @NonNull
    private String requestTag;

    @NonNull
    private final ServerImp mServerImp;
    @NonNull
    private final MemberContract.View mContractView;
    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;
    @NonNull
    private CompositeSubscription mSubscriptions;

    public MemberPresenter(@NonNull String requestTag, @NonNull ServerImp serverImp,
                           @NonNull MemberContract.View contractView,
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
    public void removeMember(String teamId, TeamMember member) {
        Map<String, String> params = new HashMap<>();
        params.put("teamId", teamId);
        params.put("memberId", member.getMemberId());
        params.put("token", (String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""));
        Subscription subscription = mServerImp
                .commonSingle(requestTag, Request.Method.POST, ServerInterface.member_delete, params, BaseResult.class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<BaseResult>() {
                    @Override
                    public void onSuccess(BaseResult result) {
                        mContractView.removeResult(true);
                    }

                    @Override
                    public void onError(Throwable error) {
                        mContractView.removeResult(false);
                        ToastUtils.toastError(error);
                        Logger.d("onError");
                    }
                });
        mSubscriptions.add(subscription);
    }
}
