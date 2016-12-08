package com.ww.lp.base.modules.team.developer;

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

public class DeveloperActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_act, true, true, false);

        setTitle("开发者认证");

        DeveloperFragment developerFragment = (DeveloperFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (developerFragment == null) {
            developerFragment = DeveloperFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    developerFragment, R.id.contentFrame);
        }

        // Create the presenter
        new DeveloperPresenter(TAG, ServerImp.getInstance(getApplicationContext()), developerFragment, SchedulerProvider.getInstance());
    }

}
