package com.ww.lp.base.utils;

import android.support.annotation.StringRes;

import com.ww.lp.base.CustomApplication;

/**
 * 获取资源文件文本
 *
 * Created by LinkedME06 on 16/11/24.
 */

public class StringResUtils {

    public static String getString(@StringRes int stringRes) {
        return CustomApplication.self().getString(stringRes);
    }

    public static void main(String[] args) {
        String abc= "aaa######bb汉字b######ccc";
        String[] a = abc.split("######");
        System.out.println("main: " + a[0]);
    }
}
