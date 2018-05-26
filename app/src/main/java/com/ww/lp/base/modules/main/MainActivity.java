package com.ww.lp.base.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.text.TextUtils;
import android.view.MenuItem;

import com.android.volley.Request;
import com.orhanobut.logger.Logger;
import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.R;
import com.ww.lp.base.data.user.LoginResult;
import com.ww.lp.base.modules.main.home.HomeFragment;
import com.ww.lp.base.modules.main.home.HomePresenter;
import com.ww.lp.base.modules.main.more.MoreFragment;
import com.ww.lp.base.modules.main.more.MorePresenter;
import com.ww.lp.base.modules.order.post.PostActivity;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.network.ServerInterface;
import com.ww.lp.base.utils.ActivityUtils;
import com.ww.lp.base.utils.SPUtils;
import com.ww.lp.base.utils.ToastUtils;
import com.ww.lp.base.utils.schedulers.SchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import rx.SingleSubscriber;

/**
 * 首页
 *
 * Created by LinkedME06 on 16/11/13.
 */

public class MainActivity extends BaseActivity {

    private BottomNavigationView bottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act, true, false, false);
        bottomBar = (BottomNavigationView) findViewById(R.id.bottomBar);
        // Set up the toolbar.
        setTitle("清软众包");
        //页面切换
        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // TODO: 16/11/25 此处需要优化，fragment重复创建
                switchTab(item.getItemId());
                switch (item.getItemId()) {
                    case R.id.va_add:
                        return false;
                    default:
                        return true;
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
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
                setTitle(getString(R.string.shouye));
                if (getSupportFragmentManager().findFragmentByTag("home") != null) {
                    showFragment("home");
                } else {
                    hideAllFragments();
                    HomeFragment fragment = HomeFragment.newInstance();
                    // Create the presenter
                    new HomePresenter(TAG, ServerImp.getInstance(getApplicationContext()), fragment, SchedulerProvider.getInstance());
                    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                            fragment, R.id.contentFrame, "home");
                }
                break;
            case R.id.va_add:
                if (TextUtils.isEmpty((String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""))) {
                    ToastUtils.toastShort("请先登录后再操作！");
                } else {
                    Intent intent = new Intent(MainActivity.this, PostActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.va_about:
                setTitle(getString(R.string.guanyu));
                if (getSupportFragmentManager().findFragmentByTag("about") != null) {
                    showFragment("about");
                } else {
                    hideAllFragments();
                    MoreFragment fragment = MoreFragment.newInstance();
                    // Create the presenter
                    new MorePresenter(TAG, ServerImp.getInstance(getApplicationContext()), fragment, SchedulerProvider.getInstance());

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
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        if (TextUtils.isEmpty((String) SPUtils.get(this, SPUtils.TOKEN, ""))) {
//            getMenuInflater().inflate(R.menu.user_login, menu);
//        } else {
//            getMenuInflater().inflate(R.menu.user_logout, menu);
//        }
//        return true;
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        menu.clear();
//        if (TextUtils.isEmpty((String) SPUtils.get(this, SPUtils.TOKEN, ""))) {
//            getMenuInflater().inflate(R.menu.user_login, menu);
//        } else {
//            getMenuInflater().inflate(R.menu.user_logout, menu);
//        }
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.login:
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//                return true;
//            case R.id.logout:
////                SPUtils.clear(MainActivity.this);
//                SPUtils.put(MainActivity.this, SPUtils.TOKEN, "");
//                SPUtils.put(MainActivity.this, SPUtils.EMAIL, "");
//                ToastUtils.toastShort("退出成功！");
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    public void logout() {
        Map<String, String> params = new HashMap<>();
        params.put("token", (String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""));
        ServerImp.getInstance(getApplicationContext())
                .commonSingle(this.getClass().getSimpleName(), Request.Method.POST, ServerInterface.logout, params, LoginResult.class)
                .subscribeOn(SchedulerProvider.getInstance().computation())
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribe(new SingleSubscriber<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //请求成功
                        SPUtils.put(MainActivity.this, SPUtils.TOKEN, "");
                        SPUtils.put(MainActivity.this, SPUtils.EMAIL, "");
                        ToastUtils.toastShort("退出成功！");
                    }

                    @Override
                    public void onError(Throwable error) {
                        ToastUtils.toastError(error);
                        Logger.d(error);
                    }
                });
    }


}
