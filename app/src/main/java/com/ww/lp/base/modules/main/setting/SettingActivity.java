package com.ww.lp.base.modules.main.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.R;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.utils.ActivityUtils;
import com.ww.lp.base.utils.schedulers.SchedulerProvider;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class SettingActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_act, true, true, false);

        setTitle("设置");

        SettingFragment settingFragment = (SettingFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (settingFragment == null) {
            settingFragment = SettingFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    settingFragment, R.id.contentFrame);
        }

        // Create the presenter
        new SettingPresenter(TAG, ServerImp.getInstance(getApplicationContext()), settingFragment, SchedulerProvider.getInstance());
    }

}
