package com.ww.lp.base.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;
import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.R;
import com.ww.lp.base.components.bottombar.BottomBar;
import com.ww.lp.base.components.bottombar.OnTabReselectListener;
import com.ww.lp.base.components.bottombar.OnTabSelectListener;
import com.ww.lp.base.modules.login.LoginActivity;
import com.ww.lp.base.modules.main.home.HomeFragment;
import com.ww.lp.base.modules.main.home.HomePresenter;
import com.ww.lp.base.modules.main.more.MoreFragment;
import com.ww.lp.base.modules.main.more.MorePresenter;
import com.ww.lp.base.modules.order.post.PostActivity;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.utils.ActivityUtils;
import com.ww.lp.base.utils.SPUtils;
import com.ww.lp.base.utils.ToastUtils;
import com.ww.lp.base.utils.schedulers.SchedulerProvider;

/**
 * 首页
 *
 * Created by LinkedME06 on 16/11/13.
 */

public class MainActivity extends BaseActivity {

    private int currentTabId = R.id.va_home;
    private BottomBar bottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act, true, false, false);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
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
                if (tabId == R.id.va_add){

                    Intent intent = new Intent(MainActivity.this, PostActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomBar.selectTabWithId(currentTabId);
    }

    /**
     * 切换tab页面
     *
     * @param tabId tabId
     */
    public void switchTab(int tabId) {
        Logger.d("tabId= " + tabId);
        switch (tabId) {
            case R.id.va_home:
                currentTabId = R.id.va_home;
                setTitle(getString(R.string.shouye));
                if (getSupportFragmentManager().findFragmentByTag("home") != null) {
                    showFragment("home");
                } else {
                    hideAllFragments();
                    HomeFragment fragment = HomeFragment.newInstance();
                    // Create the presenter
                    new HomePresenter(TAG, ServerImp.getInstance(getApplicationContext()), (HomeFragment) fragment, SchedulerProvider.getInstance());
                    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                            fragment, R.id.contentFrame, "home");
                }
                break;
            case R.id.va_add:
                Intent intent = new Intent(this, PostActivity.class);
                startActivity(intent);
                break;
            case R.id.va_about:
                currentTabId = R.id.va_about;
                setTitle(getString(R.string.guanyu));
                if (getSupportFragmentManager().findFragmentByTag("about") != null) {
                    showFragment("about");
                } else {
                    hideAllFragments();
                    MoreFragment fragment = MoreFragment.newInstance();
                    // Create the presenter
                    new MorePresenter(TAG, ServerImp.getInstance(getApplicationContext()), (MoreFragment) fragment, SchedulerProvider.getInstance());

                    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                            fragment, R.id.contentFrame, "about");
                }
                break;
        }
    }

    public void showFragment(String tag) {
        hideAllFragments();
        if (getSupportFragmentManager().findFragmentByTag(tag) != null) {
            getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag(tag)).commit();
        }
    }

    public void hideAllFragments() {
        if (getSupportFragmentManager().findFragmentByTag("home") != null) {
            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("home")).commit();
        }
        if (getSupportFragmentManager().findFragmentByTag("add") != null) {
            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("add")).commit();
        }
        if (getSupportFragmentManager().findFragmentByTag("about") != null) {
            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("about")).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (TextUtils.isEmpty((String) SPUtils.get(this, SPUtils.TOKEN, ""))) {
            getMenuInflater().inflate(R.menu.user_login, menu);
        } else {
            getMenuInflater().inflate(R.menu.user_logout, menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (TextUtils.isEmpty((String) SPUtils.get(this, SPUtils.TOKEN, ""))) {
            getMenuInflater().inflate(R.menu.user_login, menu);
        } else {
            getMenuInflater().inflate(R.menu.user_logout, menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                SPUtils.put(MainActivity.this, SPUtils.TOKEN, "");
                SPUtils.put(MainActivity.this, SPUtils.USER_ID, "");
                ToastUtils.toastShort("退出成功！");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
