package com.ww.lp.base.utils;

/**
 * Created by LinkedME06 on 16/10/29.
 */

public class DecriptHelper {

    /**
     * 密码+随机码加密
     * @param password
     * @param rnd
     * @return
     */
    public static String getEncryptedPassword(String password, String rnd){
        return Decript.SHA1(Decript.SHA1(password) + rnd);
    }

    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String getEncryptedPassword(String password){
        return Decript.SHA1(password);
    }

    public static void main(String[] args) {
       System.out.print("ddddd" +DecriptHelper.getEncryptedPassword("12345678", "0.47684905941392275"));
//       System.out.print("ddddd" +DecriptHelper.getEncryptedPassword("12345678"));
    }

}
