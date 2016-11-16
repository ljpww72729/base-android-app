package com.ww.lp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.VolleySingleton;

/**
 * Created by LinkedME06 on 16/10/26.
 */

public class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置app支持vector资源的使用，可参考https://plus.google.com/+AndroidDevelopers/posts/B7QhFkWZ6YX
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止当前页面的所有请求
        VolleySingleton.getInstance().getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return ((String) request.getTag()).contains(TAG);
            }
        });
    }

}
