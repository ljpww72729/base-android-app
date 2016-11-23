package com.ww.lp.base.modules.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;

import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.R;
import com.ww.lp.base.components.bottombar.OnTabSelectListener;
import com.ww.lp.base.databinding.MainActBinding;
import com.ww.lp.base.modules.main.home.HomeFragment;
import com.ww.lp.base.modules.main.home.HomePresenter;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.utils.ActivityUtils;
import com.ww.lp.base.utils.schedulers.SchedulerProvider;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class MainActivity extends BaseActivity {

    private MainActBinding mainActBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActBinding = DataBindingUtil.setContentView(this, R.layout.main_act);

        // Set up the toolbar.
        setSupportActionBar(mainActBinding.toolbar);
        //页面切换
        mainActBinding.bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.va_home:
                        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.contentFrame);

                        if (homeFragment == null) {
                            homeFragment = HomeFragment.newInstance();

                            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                                    homeFragment, R.id.contentFrame);
                        }

                        // Create the presenter
                        new HomePresenter(TAG, ServerImp.getInstance(getApplicationContext()), homeFragment, SchedulerProvider.getInstance());
                        break;
                    case R.id.va_add:
                        break;
                    case R.id.va_about:
                        break;
                }

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
