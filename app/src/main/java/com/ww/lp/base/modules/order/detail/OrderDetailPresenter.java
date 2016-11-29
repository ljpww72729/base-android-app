package com.ww.lp.base.modules.order.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.android.volley.Request;
import com.orhanobut.logger.Logger;
import com.ww.lp.base.components.alipay.BizContent;
import com.ww.lp.base.components.alipay.OrderInfoUtil2_0;
import com.ww.lp.base.components.alipay.PayResult;
import com.ww.lp.base.data.ProjectDetail;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.network.ServerInterface;
import com.ww.lp.base.utils.ToastUtils;
import com.ww.lp.base.utils.schedulers.BaseSchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.ww.lp.base.modules.order.detail.OrderDetailActivity.PROJECT_ID;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class OrderDetailPresenter implements OrderDetailContract.Presenter {

    @NonNull
    private String requestTag;

    @NonNull
    private final ServerImp mServerImp;
    @NonNull
    private final OrderDetailContract.View mContractView;
    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;
    @NonNull
    private CompositeSubscription mSubscriptions;
    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2016102100733937";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL3j7zDifWSiswF3\n" +
            "uJO93KXR9s/3wt0U1KNbXRLVUehT9ge53WZMlfAdCVOQnO77KIHwGqrTjDTmTV26\n" +
            "sE6AV2ulInr5Seh/eCrNEMCk0Xv3r570g/a2ukYhYcZRCuOrPP2SzAVGY+8YjEWC\n" +
            "GegKPrKFrQ9mcaf8NSCJyRkLreyJAgMBAAECgYAO8HPNAMSkujgjEWwwE2vcj9w/\n" +
            "GFr4Ub846uMzrBy5joF4siK8/aF+NoqZTHNMSe4x6tXuQp5xOv0zNpDQXECEUMhC\n" +
            "isjWcOdUHayq2DOBssRy5p2BXYHgQVdeVHMxbbdUZr0LTn0oMWsArWCCAsEPKiEp\n" +
            "KRJ5XUms1lwoKsHVQQJBAOhJZePlqhGhAoacHIFiJFho3119KQFzpcI6GGpmUbFp\n" +
            "eeuPos5gLXDR1ZZHlBeMv0GoJrvgxZM2XpXECrJxDzUCQQDRRovlXD0mpwttiqkP\n" +
            "dTTA7q8m/naR9k2XxpQXbHiCbYaHlg1Kgu2eCWCcH2qgH0kaS9h38LBftV4NyfIA\n" +
            "j66FAkBydiiVIkipozOBbU/GmbvbLOJUSSZ5pqkZilMZqw26ZIVFhGPvWglKPLwI\n" +
            "74CUEjD0g42CqwHwxqvZFN9Iitm5AkATOFpy1zTaju7ywZBjVg1hRsqZVzeGkktw\n" +
            "DBHf0NuEhxCa9UIFPN8b65qO3CfLyvPI0XxxD47zS1H3DDwIpymNAkEAj342yflU\n" +
            "79doxFYu5CBUEq9YoicM9Bvgw14R5RJJVGvxvAY6TVSUptPISQ9eb1oldETFFSAc\n" +
            "S6nftqhJKQbXsA==\n";

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtils.toastLong("支付成功");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtils.toastLong("支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    public OrderDetailPresenter(@NonNull String requestTag, @NonNull ServerImp serverImp,
                                @NonNull OrderDetailContract.View contractView,
                                @NonNull BaseSchedulerProvider schedulerProvider) {
        this.requestTag = requestTag;
        mServerImp = checkNotNull(serverImp, "serverImp cannot be null!");
        mContractView = checkNotNull(contractView, "loginView cannot be null!");
        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeSubscription();
        mContractView.setPresenter(this);

    }

    @Override
    public void subscribe() {
        //此处为页面打开后开始加载数据时调用的方法
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    /**
     * 请求轮播图列表
     */
    @Override
    public void loadData(String projectId) {
        Map<String, String> params = new HashMap<>();
        params.put(PROJECT_ID, projectId);
        Subscription subscription = mServerImp
                .commonSingle(requestTag, Request.Method.POST, ServerInterface.project_detail, params, ProjectDetail.class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<ProjectDetail>() {
                    @Override
                    public void onSuccess(ProjectDetail projectDetail) {
//                        mView.removeProgressDialog();
                        //请求成功
//                        mView.success(loginResult);

                        mContractView.showDetail(projectDetail);


                    }

                    @Override
                    public void onError(Throwable error) {
                        System.out.print(error);
//                        mView.removeProgressDialog();
                        ToastUtils.toastShort(error.getCause().getMessage());
                        Logger.d(error.getCause().getMessage());
                    }
                });
        mSubscriptions.add(subscription);

    }

    @Override
    public void pay(final Activity activity, BizContent bizContent) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        String bizContentStr = bizContent.toJsonStr();
        if (bizContentStr != null) {
            Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, bizContentStr);
            String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
            String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
            final String orderInfo = orderParam + "&" + sign;

            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    PayTask alipay = new PayTask(activity);
                    Map<String, String> result = alipay.payV2(orderInfo, true);
                    Log.i("msp", result.toString());

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };

            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }
    }


}
