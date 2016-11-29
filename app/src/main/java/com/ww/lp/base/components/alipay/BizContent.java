package com.ww.lp.base.components.alipay;

import com.google.gson.Gson;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

/**
 * 订单参数 生成json格式的对象进行传递
 *
 * Created by LinkedME06 on 16/11/26.
 */

public class BizContent {

    //对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。描述：Iphone6 16G
    private String body;
    //商品的标题/交易标题/订单标题/订单关键字等。示例：大乐透
    @NonNull
    private String subject;
    //商户网站唯一订单号 示例：70501111111S001111119
    @NonNull
    private String out_trade_no;
    // 该笔订单允许的最晚付款时间，逾期将关闭交易。
    // 取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
    // 该参数数值不接受小数点， 如 1.5h，可转换为 90m。 示例：90m
    private String timeout_express;
    //订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 示例：9.00
    @NonNull
    private String total_amount;
    //收款支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID 示例：2088102147948060
    private String seller_id;
    //针对用户授权接口，获取用户相关数据时，用于标识用户授权关系 示例：appopenBb64d181d0146481ab6a762c00714cC27
    private String auth_token;
    //销售产品码，商家和支付宝签约的产品码 示例：销售产品码，商家和支付宝签约的产品码 示例：QUICK_WAP_PAY
    @NonNull
    private String product_code;
    //商品主类型：0—虚拟类商品，1—实物类商品 注：虚拟类商品不支持使用花呗渠道 示例：0
    private String goods_type;
    // 公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。
    // 支付宝会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝
    // 示例：merchantBizType%3d3C%26merchantBizNo%3d2016010101111
    private String passback_params;
    // 优惠参数 注：仅与支付宝协商后可用 示例：{"storeIdType":"1"}
    private String promo_params;
    // 业务扩展参数，详见下面的“业务扩展参数说明”  示例：{"sys_service_provider_id":"2088511833207846"}
    private String extend_params;
    // 可用渠道，用户只能在指定渠道范围内支付 当有多个渠道时用“,”分隔 注：与disable_pay_channels互斥 示例：pcredit,moneyFund,debitCardExpress
    private String enable_pay_channels;
    //禁用渠道，用户不可用指定渠道支付 当有多个渠道时用“,”分隔 注：与enable_pay_channels互斥 示例：pcredit,moneyFund,debitCardExpress
    private String disable_pay_channels;

    public BizContent(String body, @NonNull String subject, @NonNull String out_trade_no, String timeout_express, @NonNull String total_amount, @NonNull String product_code) {

        this.body = body;
        this.subject = subject;
        this.out_trade_no = out_trade_no;
        if (TextUtils.isEmpty(timeout_express)) {
            this.timeout_express = "90m";
        } else {
            this.timeout_express = timeout_express;
        }
        this.total_amount = total_amount;
        this.product_code = product_code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTimeout_express() {
        return timeout_express;
    }

    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getPassback_params() {
        return passback_params;
    }

    public void setPassback_params(String passback_params) {
        this.passback_params = passback_params;
    }

    public String getPromo_params() {
        return promo_params;
    }

    public void setPromo_params(String promo_params) {
        this.promo_params = promo_params;
    }

    public String getExtend_params() {
        return extend_params;
    }

    public void setExtend_params(String extend_params) {
        this.extend_params = extend_params;
    }

    public String getEnable_pay_channels() {
        return enable_pay_channels;
    }

    public void setEnable_pay_channels(String enable_pay_channels) {
        this.enable_pay_channels = enable_pay_channels;
    }

    public String getDisable_pay_channels() {
        return disable_pay_channels;
    }

    public void setDisable_pay_channels(String disable_pay_channels) {
        this.disable_pay_channels = disable_pay_channels;
    }

    public String toJsonStr() {
        if (TextUtils.isEmpty(subject)) {
            Logger.d("subject is not empty!");
            return null;
        }
        if (TextUtils.isEmpty(out_trade_no)) {
            Logger.d("out_trade_no is not empty!");
            return null;
        }
        if (TextUtils.isEmpty(total_amount)) {
            Logger.d("total_amount is not empty!");
            return null;
        }
        if (TextUtils.isEmpty(product_code)) {
            Logger.d("product_code is not empty!");
            return null;
        }
        return new Gson().toJson(this);
    }

}
