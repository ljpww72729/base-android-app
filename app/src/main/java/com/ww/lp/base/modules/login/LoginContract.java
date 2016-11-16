package com.ww.lp.base.modules.login;

import android.support.annotation.NonNull;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;
import com.ww.lp.base.entity.UserInfo;


/**
 * Created by LinkedME06 on 16/10/27.
 */

public class LoginContract {

    interface View extends BaseView<Presenter> {
        void success();
    }

    interface Presenter extends BasePresenter {

        void login(@NonNull UserInfo userInfo);

    }

}
