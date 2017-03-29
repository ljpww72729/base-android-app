package com.ww.lp.base.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by LinkedME06 on 16/11/26.
 */

public class Constants {
    public static final String TITLE = "title";
    //    0:已发布（默认）
//    1：已被承接（可拒绝）
//    2：已完成未支付
//    3：完成且已支付
    public static final String ORDER_STATUS = "order_status";
    public static final int PUBLISH = 0;
    public static final int ACCEPT = 1;
    public static final int UNPAYED = 2;
    public static final int PAYED = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PUBLISH, ACCEPT, UNPAYED, PAYED})
    public @interface OrderStatus {}

    public static final int NORMAL = 0;
    public static final int DEVELOPER = 1;
    public static final int ADMIN = 2;

    public static final String PROJECTIMGS = "projectImgs";

    public static final String DES_SEPARATOR = "#######";

}
