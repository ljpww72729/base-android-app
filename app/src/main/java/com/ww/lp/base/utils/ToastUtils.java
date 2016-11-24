package com.ww.lp.base.utils;

import android.widget.Toast;

import com.ww.lp.base.CustomApplication;

/**
 * 显示消息
 *
 * Created by LinkedME06 on 16/11/24.
 */

public class ToastUtils {

    public static void toastShort(String msg) {
        Toast.makeText(CustomApplication.self(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(String msg) {
        Toast.makeText(CustomApplication.self(), msg, Toast.LENGTH_LONG).show();
    }

}
