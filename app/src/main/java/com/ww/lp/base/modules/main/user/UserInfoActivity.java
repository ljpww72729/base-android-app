package com.ww.lp.base.modules.main.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.R;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.utils.ActivityUtils;
import com.ww.lp.base.utils.schedulers.SchedulerProvider;

/**
 * 登录页面
 * Created by LinkedME06 on 16/10/27.
 */

public class UserInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_act, true, true, false);
        setTitle("个人信息");

        UserInfoFragment userInfoFragment = (UserInfoFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (userInfoFragment == null) {
            userInfoFragment = UserInfoFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    userInfoFragment, R.id.contentFrame);
        }

        // Create the presenter
        new UserInfoPresenter(TAG, ServerImp.getInstance(getApplicationContext()), userInfoFragment, SchedulerProvider.getInstance());
    }

}
