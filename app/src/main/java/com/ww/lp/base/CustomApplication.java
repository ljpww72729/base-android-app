package com.ww.lp.base;

import android.app.Application;

import com.android.volley.toolbox.VolleySingleton;
import com.facebook.drawee.backends.pipeline.*;
import com.facebook.drawee.backends.pipeline.BuildConfig;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by LinkedME06 on 16/11/12.
 */

public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化volley
        VolleySingleton.init(this);
        //初始化Fresco图片加载库
        Fresco.initialize(this);
        //logger配置

        if (BuildConfig.DEBUG) {
            Logger.init("lp_log");
        }else {
            //release版本需要隐藏日志
            Logger.init("lp_log").logLevel(LogLevel.NONE);
        }
    }
}
