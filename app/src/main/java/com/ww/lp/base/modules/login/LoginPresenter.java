package com.ww.lp.base.modules.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.ww.lp.base.entity.UserInfo;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.network.ServerInterface;
import com.ww.lp.base.utils.schedulers.BaseSchedulerProvider;

import junit.framework.TestResult;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/10/27.
 */

public class LoginPresenter implements LoginContract.Presenter {

    @NonNull
    private String requestTag;

    @NonNull
    private final ServerImp mServerImp;
    @NonNull
    private final LoginContract.View mLoginView;
    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;
    @NonNull
    private CompositeSubscription mSubscriptions;

    public LoginPresenter(@NonNull String requestTag, @NonNull ServerImp serverImp,
                          @NonNull LoginContract.View loginView,
                          @NonNull BaseSchedulerProvider schedulerProvider) {
        this.requestTag = requestTag;
        mServerImp = checkNotNull(serverImp, "serverImp cannot be null!");
        mLoginView = checkNotNull(loginView, "loginView cannot be null!");
        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeSubscription();
        mLoginView.setPresenter(this);
    }


    @Override
    public void login(@NonNull UserInfo userInfo) {
//        Subscription subscription = mServerImp
//                .login(requestTag, student)
//                .subscribeOn(mSchedulerProvider.computation())
//                .observeOn(mSchedulerProvider.ui())
//                .subscribe(new Observer<LoginResult>() {
//                    @Override
//                    public void onCompleted() {
//                        //mTaskDetailView.setLoadingIndicator(false);
//                        Log.d("ddd", "onCompleted: ");
//                        mLoginView.success();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("ddd", "onError: ");
//
//                    }
//
//                    @Override
//                    public void onNext(LoginResult loginResult) {
//                        Log.d("ddd", "onNext: " + loginResult.getCode() + "ddd" + loginResult.getToken());
//                        //showTask(task);
//                    }
//                });
//        mSubscriptions.add(subscription);

//        Map<String, String> paramLogin = new HashMap<>();
//        paramLogin.put("sid", student.getSid());
//        paramLogin.put("password", DecriptHelper.getEncryptedPassword(student.getPassword(), "0.6473738071851545"));
//        Subscription subscription = mServerImp
//                .common(requestTag, Request.Method.POST, ServerInterface.login, paramLogin, LoginResult.class)
//                .subscribeOn(mSchedulerProvider.computation())
//                .observeOn(mSchedulerProvider.ui())
//                .subscribe(new Observer<LoginResult>() {
//                    @Override
//                    public void onCompleted() {
//                        //mTaskDetailView.setLoadingIndicator(false);
//                        Log.d("ddd", "onCompleted: ");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("ddd", "onError: ");
//                    }
//
//                    @Override
//                    public void onNext(LoginResult loginResult) {
//                        Log.d("ddd", "onNext: ");
//                        //showTask(task);
//                    }
//                });
//        mSubscriptions.add(subscription);

        Map<String, String> params = new HashMap<>();
        params.put("type","test110");
        params.put("postid", "dd");
        Subscription subscription = mServerImp
                .common(requestTag, Request.Method.GET, ServerInterface.login, params, TestResult.class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<TestResult>() {
                    @Override
                    public void onCompleted() {
                        //mTaskDetailView.setLoadingIndicator(false);
                        Log.d("ddd", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ddd", "onError: ");
                    }

                    @Override
                    public void onNext(TestResult loginResult) {
                        Log.d("ddd", "onNext: ");
                        //showTask(task);
                    }
                });
        mSubscriptions.add(subscription);

    }

    @Override
    public void subscribe() {
        //此处为页面打开后开始加载数据时调用的方法
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }
}
