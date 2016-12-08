package com.ww.lp.base.modules.main.user;

import android.support.annotation.NonNull;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;
import com.ww.lp.base.data.ServerResult;
import com.ww.lp.base.entity.UserInfo;


/**
 * Created by LinkedME06 on 16/10/27.
 */

public class UserInfoContract {

    interface View extends BaseView<Presenter> {
        void success(ServerResult serverResult);
    }

    interface Presenter extends BasePresenter {

        void modify(@NonNull UserInfo userInfo);

    }

}
