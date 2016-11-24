package com.ww.lp.base.network;

/**
 * 服务器请求接口
 * Created by LinkedME06 on 16/10/29.
 */

public class ServerInterface {

    public static final String baseUrl = "http://118.178.225.234:8080/api/";


    public static final String testUrl = "http://www.kuaidi100.com/query";
    /**
     * 获取随机数
     */
    public static final String rnd = baseUrl + "passport/rnd?sid=test110";
    /**
     * 注册接口
     */
    public static final String reg= baseUrl + "passport/reg";
    /**
     * 登录接口
     */
    public static final String login = baseUrl + "user/login";
    /**
     * 轮播图请求接口
     */
    public static final String carousel = baseUrl + "carousel";
    /**
     * 上传文件请求接口
     */
    public static final String uploadFile = baseUrl + "upload";


}
