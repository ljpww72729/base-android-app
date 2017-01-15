package com.ww.lp.base.modules.main.user;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.volley.Request;
import com.orhanobut.logger.Logger;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.R;
import com.ww.lp.base.data.CommonResult;
import com.ww.lp.base.data.user.UserInfo;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.network.ServerInterface;
import com.ww.lp.base.utils.Constants;
import com.ww.lp.base.utils.SPUtils;
import com.ww.lp.base.utils.StringResUtils;
import com.ww.lp.base.utils.ToastUtils;
import com.ww.lp.base.utils.schedulers.BaseSchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 登录Presenter
 *
 * Created by LinkedME06 on 16/10/27.
 */

public class UserInfoPresenter implements UserInfoContract.Presenter {

    @NonNull
    private String requestTag;

    @NonNull
    private final ServerImp mServerImp;
    @NonNull
    private final UserInfoContract.View mView;
    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;
    @NonNull
    private CompositeSubscription mSubscriptions;

    public UserInfoPresenter(@NonNull String requestTag, @NonNull ServerImp serverImp,
                             @NonNull UserInfoContract.View loginView,
                             @NonNull BaseSchedulerProvider schedulerProvider) {
        this.requestTag = requestTag;
        mServerImp = checkNotNull(serverImp, "serverImp cannot be null!");
        mView = checkNotNull(loginView, "loginView cannot be null!");
        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void modify(@NonNull UserInfo userInfo) {
        if (validate(userInfo)) {
            Map<String, String> params = new HashMap<>();
            params.put("email", userInfo.getEmail());
            params.put("password", userInfo.getPassword());
            params.put("isAdmin", (int) SPUtils.get(CustomApplication.self(), SPUtils.IS_ADMIN, Constants.NORMAL) + "");
            params.put("isDeveloper", (int) SPUtils.get(CustomApplication.self(), SPUtils.IS_DEVELOPER, Constants.NORMAL) + "");
            params.put("userId", (String) SPUtils.get(CustomApplication.self(), SPUtils.USER_ID, ""));
            params.put("phoneNum", userInfo.getPhoneNum());
            params.put("token", (String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""));
            Subscription subscription = mServerImp
                    .commonSingle(requestTag, Request.Method.POST, ServerInterface.edit, params, CommonResult.class)
                    .subscribeOn(mSchedulerProvider.computation())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(new SingleSubscriber<CommonResult>() {
                        @Override
                        public void onSuccess(CommonResult commonResult) {
                            //请求成功
                            mView.success(commonResult.isData());
                        }

                        @Override
                        public void onError(Throwable error) {
                            mView.success(false);
                            ToastUtils.toastError(error);
                            Logger.d(error);
                        }
                    });
            mSubscriptions.add(subscription);
        }
    }

    public boolean validate(UserInfo userInfo) {
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
