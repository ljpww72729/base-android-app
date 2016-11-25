package com.ww.lp.base.modules.templates;

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

public class TempActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_act, true, true, false);

        setTitle("Temp");

        TempFragment tempFragment = (TempFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (tempFragment == null) {
            tempFragment = TempFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    tempFragment, R.id.contentFrame);
        }

        // Create the presenter
        new TempPresenter(TAG, ServerImp.getInstance(getApplicationContext()), tempFragment, SchedulerProvider.getInstance());
    }

}
