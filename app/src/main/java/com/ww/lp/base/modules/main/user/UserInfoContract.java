package com.ww.lp.base.modules.main.user;

import android.support.annotation.NonNull;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;
import com.ww.lp.base.data.user.UserInfo;


/**
 * Created by LinkedME06 on 16/10/27.
 */

public class UserInfoContract {

    interface View extends BaseView<Presenter> {
        void success(boolean result);
    }

    interface Presenter extends BasePresenter {

        void modify(@NonNull UserInfo userInfo);

    }

}
