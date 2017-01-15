package com.ww.lp.base.modules.order.detail;

import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.android.volley.Request;
import com.orhanobut.logger.Logger;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.data.CommonResult;
import com.ww.lp.base.data.pay.SignResult;
import com.ww.lp.base.data.project.ProjectInfo;
import com.ww.lp.base.data.project.ProjectListResult;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.network.ServerInterface;
import com.ww.lp.base.utils.Constants;
import com.ww.lp.base.utils.SPUtils;
import com.ww.lp.base.utils.ToastUtils;
import com.ww.lp.base.utils.schedulers.BaseSchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.functions.Func0;
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

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
//                    PayResult aliPay = new PayResult((Map<String, String>) msg.obj);
                    mContractView.aliPay(true, (Map<String, String>) msg.obj);
//                    /**
//                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
//                     */
//                    String resultInfo = aliPay.getResult();// 同步返回需要验证的信息
//                    String resultStatus = aliPay.getResultStatus();
//                    // 判断resultStatus 为9000则代表支付成功
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        ToastUtils.toastLong("支付成功");
//                    } else {
//                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        ToastUtils.toastLong("支付失败");
//                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
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

    @Override
    public void getSign(String projectId) {

        Map<String, String> params = new HashMap<>();
        params.put(PROJECT_ID, projectId);
        params.put("token", (String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""));
        Subscription subscription = mServerImp
                .commonSingle(requestTag, Request.Method.POST, ServerInterface.get_sign, params, SignResult.class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<SignResult>() {
                    @Override
                    public void onSuccess(SignResult signResult) {
                        if (!TextUtils.isEmpty(signResult.getData())) {
                            mContractView.signResult(true, signResult.getData());
                        } else {
                            mContractView.signResult(false, "");
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        mContractView.signResult(false, "");
//                        ToastUtils.toastError(error);
                        Logger.d(error);
                    }
                });
        mSubscriptions.add(subscription);


    }

    @Override
    public void pay(final Activity activity, final String signStr) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
//        Runnable payRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
//                PayTask alipay = new PayTask(activity);
//                Map<String, String> result = alipay.payV2(signStr, true);
//                Log.i("msp", result.toString());
//
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
        // TODO: 28/12/2016 线上版本一定要将此注释掉
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        final PayTask alipay = new PayTask(activity);
        Single.defer(new Func0<Single<Map<String, String>>>() {
            @Override
            public Single<Map<String, String>> call() {
                return Single.just(alipay.payV2(signStr, true));
            }
        }).subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<Map<String, String>>() {
                    @Override
                    public void onSuccess(Map<String, String> payMap) {
                        Logger.d(payMap);
                        mContractView.aliPay(true, payMap);
                    }

                    @Override
                    public void onError(Throwable error) {
                        mContractView.aliPay(false, new HashMap<String, String>());
                        Logger.d(error);
                    }
                });
    }

    @Override
    public void deleteProject(String projectId) {
        Map<String, String> params = new HashMap<>();
        params.put(PROJECT_ID, projectId);
        params.put("token", (String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""));
        Subscription subscription = mServerImp
                .commonSingle(requestTag, Request.Method.POST, ServerInterface.project_delete, params, CommonResult.class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<CommonResult>() {
                    @Override
                    public void onSuccess(CommonResult commonResult) {
                        mContractView.deleteSuccess(commonResult.isData());
                    }

                    @Override
                    public void onError(Throwable error) {
                        mContractView.deleteSuccess(false);
                        ToastUtils.toastError(error);
                        Logger.d(error);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void editProject(ProjectInfo projectInfo, int status) {
        Map<String, String> params = new HashMap<>();
        params.put("projectId", projectInfo.getProjectId());
        params.put("status", status + "");
        // TODO: 16/11/29 此处title拼写错误
        params.put("tittle", projectInfo.getTittle());
        params.put("describes", projectInfo.getDescribes());
        params.put("price", projectInfo.getPrice());
        params.put("phoneNum", projectInfo.getPhoneNum());
        String img = "";
        if (projectInfo.getProjectImgs() != null && projectInfo.getProjectImgs().size() > 0) {
            img = projectInfo.getProjectImgs().get(0).getImg();
        }
        params.put("img", img);
        params.put(Constants.PROJECTIMGS, new Gson().toJson(projectInfo.getProjectImgs()));
        params.put("token", (String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""));
        Subscription subscription = mServerImp
                .commonSingle(requestTag, Request.Method.POST, ServerInterface.project_edit, params, CommonResult.class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<CommonResult>() {
                    @Override
                    public void onSuccess(CommonResult commonResult) {
//                        mView.removeProgressDialog();
                        //请求成功
//                        mView.success(loginResult);
//                        mContractView.updateOrderList(arrayList);
                        mContractView.editSuccess(commonResult.isData());
                    }

                    @Override
                    public void onError(Throwable error) {
//                        mView.removeProgressDialog();
                        mContractView.editSuccess(false);
                        ToastUtils.toastError(error);
                        Logger.d("onError");
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void loadProjectInfo(String projectId) {
        Map<String, String> params = new HashMap<>();
        params.put("pageIndex", "1");
        params.put("projectId", projectId);
        params.put("isOnlyQueryMyPublis", "0");
        params.put("token", (String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""));
        Subscription subscription = mServerImp
                .commonSingle(requestTag, Request.Method.POST, ServerInterface.project_list, params, ProjectListResult.class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<ProjectListResult>() {
                    @Override
                    public void onSuccess(ProjectListResult projectList) {
                        mContractView.showDetail(projectList.getData().getList().get(0));
                    }

                    @Override
                    public void onError(Throwable error) {
                        ToastUtils.toastError(error);
                        Logger.d(error);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void acceptProject(String projectId) {
        Map<String, String> params = new HashMap<>();
        params.put("teamId", (String) SPUtils.get(CustomApplication.self(), SPUtils.TEAM_ID, ""));
        params.put("projectId", projectId);
        params.put("token", (String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""));
        Subscription subscription = mServerImp
                .commonSingle(requestTag, Request.Method.POST, ServerInterface.accept_project, params, CommonResult.class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<CommonResult>() {
                    @Override
                    public void onSuccess(CommonResult commonResult) {
                        mContractView.acceptProjectResult(commonResult.isData());
                    }

                    @Override
                    public void onError(Throwable error) {
                        mContractView.acceptProjectResult(false);
                        ToastUtils.toastError(error);
                        Logger.d(error);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getPayResult(String payResult) {
        Map<String, String> params = new HashMap<>();
        params.put("beToVerify", payResult);
        params.put("token", (String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""));
        Subscription subscription = mServerImp
                .commonSingle(requestTag, Request.Method.POST, ServerInterface.project_verify, params, CommonResult.class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new SingleSubscriber<CommonResult>() {
                    @Override
                    public void onSuccess(CommonResult commonResult) {
                        mContractView.payResult(commonResult.isData());
                    }

                    @Override
                    public void onError(Throwable error) {
                        mContractView.payResult(false);
                        ToastUtils.toastError(error);
                        Logger.d(error);
                    }
                });
        mSubscriptions.add(subscription);
    }


}
