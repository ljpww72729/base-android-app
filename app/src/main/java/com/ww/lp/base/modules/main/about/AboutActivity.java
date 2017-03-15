package com.ww.lp.base.modules.main.about;

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

public class AboutActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_act, true, true, false);

        setTitle("Temp");

        AboutFragment tempFragment = (AboutFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (tempFragment == null) {
            tempFragment = AboutFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    tempFragment, R.id.contentFrame);
        }

        // Create the presenter
        new AboutPresenter(TAG, ServerImp.getInstance(getApplicationContext()), tempFragment, SchedulerProvider.getInstance());
    }

}
