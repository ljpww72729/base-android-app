package com.ww.lp.base.modules.login;

import android.support.annotation.NonNull;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;
import com.ww.lp.base.data.user.LoginResult;
import com.ww.lp.base.data.user.UserInfo;


/**
 * Created by LinkedME06 on 16/10/27.
 */

public class LoginContract {

    interface View extends BaseView<Presenter> {
        void success(LoginResult loginResult);
        void showProgressDialog(String msg);
        void removeProgressDialog();
        void verificationResult(boolean result);
    }

    interface Presenter extends BasePresenter {
        void verification(UserInfo userInfo);
        void register(@NonNull UserInfo userInfo);
        void login(@NonNull UserInfo userInfo);

    }

}
