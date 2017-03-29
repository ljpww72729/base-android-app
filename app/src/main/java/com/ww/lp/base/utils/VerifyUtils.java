package com.ww.lp.base.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by LinkedME06 on 24/03/2017.
 */

public class VerifyUtils {

    /**
     * 验证手机号码
     */
    public static boolean isPhoneNum(String phoneNum) {
        boolean validate = false;
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtils.toastShort("请输入手机号！");
            return false;
        } else {
            String phoneRegex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,1,3,5-8])|(18[0-9])|(147))\\d{8}$";
            validate = Pattern.matches(phoneRegex, phoneNum);
        }
        if (!validate) {
            ToastUtils.toastShort("请输入正确的手机号！");
        }
        return validate;
    }
}
