package com.ww.lp.base;

import android.app.Application;

import com.android.volley.toolbox.VolleySingleton;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by LinkedME06 on 16/11/12.
 */

public class CustomApplication extends Application {

    private static CustomApplication app;

    public static CustomApplication self() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (app == null) {
            app = this;
        }
        //初始化volley
        VolleySingleton.init(this);
        //初始化Fresco图片加载库
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(getApplicationContext())
                .setDownsampleEnabled(true)// This option can help prove the performance.
                //other settings
                .build();
        Fresco.initialize(this, config);
        //logger配置

        if (BuildConfig.DEBUG) {
            Logger.init("lp_log");
        } else {
            //release版本需要隐藏日志
            Logger.init("lp_log").logLevel(LogLevel.NONE);
        }

    }
}
