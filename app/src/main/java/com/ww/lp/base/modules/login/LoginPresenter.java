package com.ww.lp.base.modules.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.volley.Request;
import com.orhanobut.logger.Logger;
import com.ww.lp.base.R;
import com.ww.lp.base.data.CommonStringResult;
import com.ww.lp.base.data.user.LoginResult;
import com.ww.lp.base.data.user.UserInfo;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.network.ServerInterface;
import com.ww.lp.base.utils.StringResUtils;
import com.ww.lp.base.utils.VerifyUtils;
import com.ww.lp.base.utils.ToastUtils;
import com.ww.lp.base.utils.schedulers.BaseSchedulerProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 登录Presenter
 *
 * Created by LinkedME06 on 16/10/27.
 */

public class LoginPresenter implements LoginContract.Presenter {

    @NonNull
    private String requestTag;

    @NonNull
    private final ServerImp mServerImp;
    @NonNull
    private final LoginContract.View mView;
    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;
    @NonNull
    private CompositeSubscription mSubscriptions;

    public LoginPresenter(@NonNull String requestTag, @NonNull ServerImp serverImp,
                          @NonNull LoginContract.View loginView,
                          @NonNull BaseSchedulerProvider schedulerProvider) {
        this.requestTag = requestTag;
        mServerImp = checkNotNull(serverImp, "serverImp cannot be null!");
        mView = checkNotNull(loginView, "loginView cannot be null!");
        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }


    @Override
    public void verification(UserInfo userInfo) {
        if (validate(userInfo) && VerifyUtils.isPhoneNum(userInfo.getPhoneNum())) {
            Map<String, String> params = new HashMap<>();
            params.put("email", userInfo.getEmail());
            params.put("password", userInfo.getPassword());
            params.put("phoneNum", userInfo.getPhoneNum());
            Subscription subscription = mServerImp
                    .commonSingle(requestTag, Request.Method.POST, ServerInterface.verifycation_code, params, CommonStringResult.class)
                    .subscribeOn(mSchedulerProvider.computation())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(new SingleSubscriber<CommonStringResult>() {
                        @Override
                        public void onSuccess(CommonStringResult commonStringResult) {
                            mView.removeProgressDialog();
                            //请求成功
                            mView.verificationResult(true);
                        }

                        @Override
                        public void onError(Throwable error) {
                            mView.verificationResult(false);
                            ToastUtils.toastError(error);
                            Logger.d(error);
                        }
                    });
            mSubscriptions.add(subscription);
        }
    }



    @Override
    public void register(@NonNull UserInfo userInfo) {
        if (validate(userInfo) && VerifyUtils.isPhoneNum(userInfo.getPhoneNum()) && validateCode(userInfo)) {
            mView.showProgressDialog(null);
            Map<String, String> params = new HashMap<>();
            params.put("email", userInfo.getEmail());
            params.put("password", userInfo.getPassword());
            params.put("phoneNum", userInfo.getPhoneNum());
            params.put("verificationCode", userInfo.getVerificationCode());
            Subscription subscription = mServerImp
                    .commonSingle(requestTag, Request.Method.POST, ServerInterface.reg, params, LoginResult.class)
                    .subscribeOn(mSchedulerProvider.computation())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(new SingleSubscriber<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            mView.removeProgressDialog();
                            //请求成功
                            mView.success(loginResult);
                        }

                        @Override
                        public void onError(Throwable error) {
                            mView.removeProgressDialog();
                            ToastUtils.toastError(error);
                            Logger.d(error);
                        }
                    });
            mSubscriptions.add(subscription);
        }
    }

    private boolean validateCode(UserInfo userInfo) {
        if (!TextUtils.isEmpty(userInfo.getVerificationCode()) && Pattern.matches("^\\d{6}$", userInfo.getVerificationCode())){
            return true;
        }else{
            ToastUtils.toastShort("验证码只能为6位数字");
            return false;
        }
    }

    @Override
    public void login(@NonNull UserInfo userInfo) {
        if (validate(userInfo)) {
            mView.showProgressDialog(null);
            Map<String, String> params = new HashMap<>();
            params.put("email", userInfo.getEmail());
            params.put("password", userInfo.getPassword());
            Subscription subscription = mServerImp
                    .commonSingle(requestTag, Request.Method.POST, ServerInterface.login, params, LoginResult.class)
                    .subscribeOn(mSchedulerProvider.computation())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(new SingleSubscriber<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            mView.removeProgressDialog();
                            //请求成功
                            mView.success(loginResult);
                        }

                        @Override
                        public void onError(Throwable error) {
                            mView.removeProgressDialog();
                            ToastUtils.toastError(error);
                            Logger.d(error);
                        }
                    });
            mSubscriptions.add(subscription);
        }
    }

    public boolean validate(com.ww.lp.base.data.user.UserInfo userInfo) {
        boolean valid = true;

        String email = userInfo.getEmail();
        String password = userInfo.getPassword();
        Logger.d(userInfo);

        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            valid = false;
            ToastUtils.toastShort(StringResUtils.getString(R.string.error_invalid_email));
        }

        if (TextUtils.isEmpty(password) || password.length() < 4 || password.length() > 20) {
            valid = false;
            ToastUtils.toastShort(StringResUtils.getString(R.string.error_invalid_password));
        }
        return valid;
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
