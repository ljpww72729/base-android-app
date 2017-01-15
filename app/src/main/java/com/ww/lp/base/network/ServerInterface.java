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
    public static final String reg= baseUrl + "user/add";
    /**
     * 登录接口
     */
    public static final String login = baseUrl + "user/login";
    /**
     * 短信验证码接口
     */
    public static final String verifycation_code = baseUrl + "user/verifycationcode";
    /**
     * 退出接口
     */
    public static final String logout = baseUrl + "user/logout";
    /**
     * 修改用户信息接口
     */
    public static final String edit = baseUrl + "user/edit";
    /**
     * 轮播图请求接口
     */
    public static final String carousel = baseUrl + "ads/list";
    /**
     * 所有项目列表接口
     */
    public static final String project_list = baseUrl + "project/list";
    /**
     * 项目详情接口
     */
    public static final String project_detail = baseUrl + "project/detail";
    /**
     * 用户订单列表接口
     */
    public static final String user_order_list = baseUrl + "project/usercenter";
    /**
     * 发布需求接口
     */
    public static final String project_publish = baseUrl + "project/publish";
    /**
     * 修改需求接口
     */
    public static final String project_edit = baseUrl + "project/edit";
    /**
     * 删除需求接口
     */
    public static final String project_delete = baseUrl + "project/delete";
    /**
     * 上传文件请求接口
     */
    public static final String uploadFile = baseUrl + "img/upload";
    /**
     * 团队列表接口
     */
    public static final String team_list = baseUrl + "team/list";
    /**
     * 添加成员请求接口
     */
    public static final String member_add = baseUrl + "member/add";
    /**
     * 修改成员信息接口
     */
    public static final String member_edit = baseUrl + "member/edit";
    /**
     * 移除成员请求接口
     */
    public static final String member_delete = baseUrl + "member/delete";
    /**
     * 承接项目接口
     */
    public static final String accept_project = baseUrl + "team/takeproject";
    /**
     * 已承接项目接口
     */
    public static final String team_project_list = baseUrl + "team/projectlist";
    /**
     * 支付加密串获取接口
     */
    public static final String get_sign = baseUrl + "project/getsign";
    /**
     * 评价接口
     */
    public static final String team_score = baseUrl + "team/score";
    /**
     * 支付结果确认接口
     */
    public static final String project_verify = baseUrl + "project/verify";


}
