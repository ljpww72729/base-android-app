package com.ww.lp.base.modules.main;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.orhanobut.logger.Logger;
import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.components.bottombar.BottomBar;
import com.ww.lp.base.components.bottombar.OnTabReselectListener;
import com.ww.lp.base.components.bottombar.OnTabSelectListener;
import com.ww.lp.base.modules.main.home.HomeFragment;
import com.ww.lp.base.modules.main.home.HomePresenter;
import com.ww.lp.base.modules.main.more.MoreFragment;
import com.ww.lp.base.modules.main.more.MorePresenter;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.utils.ActivityUtils;
import com.ww.lp.base.utils.schedulers.SchedulerProvider;

import java.lang.ref.WeakReference;

/**
 * 首页
 *
 * Created by LinkedME06 on 16/11/13.
 */

public class MainActivity extends BaseActivity {

    private SparseArray<WeakReference<BaseFragment>> fragmentList = new SparseArray<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act, true, false, false);
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        // Set up the toolbar.
        setTitle("Base App");
        //页面切换
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                // TODO: 16/11/25 此处需要优化，fragment重复创建
                switchTab(tabId);
            }
        });
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                Logger.d("reselect tabId= " + tabId);
            }
        });

    }

    /**
     * 切换tab页面
     *
     * @param tabId tabId
     */
    public void switchTab(int tabId) {
        Logger.d("tabId= " + tabId);
        BaseFragment fragment = fragmentList.get(tabId) == null ? null : fragmentList.get(tabId).get();
        switch (tabId) {
            case R.id.va_home:
                if (fragment == null) {
                    fragment = HomeFragment.newInstance();
                    WeakReference<BaseFragment> reference = new WeakReference<BaseFragment>(fragment);
                    fragmentList.put(tabId, reference);

                    // Create the presenter
                    new HomePresenter(TAG, ServerImp.getInstance(getApplicationContext()), (HomeFragment) fragment, SchedulerProvider.getInstance());

                }
                ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(),
                        fragment, R.id.contentFrame);
                break;
            case R.id.va_add:
                break;
            case R.id.va_about:
                if (fragment == null) {
                    fragment = MoreFragment.newInstance();
                    WeakReference<BaseFragment> reference = new WeakReference<BaseFragment>(fragment);
                    fragmentList.put(tabId, reference);

                    // Create the presenter
                    new MorePresenter(TAG, ServerImp.getInstance(getApplicationContext()), (MoreFragment) fragment, SchedulerProvider.getInstance());

                }
                ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(),
                        fragment, R.id.contentFrame);
                break;
        }
    }
}
