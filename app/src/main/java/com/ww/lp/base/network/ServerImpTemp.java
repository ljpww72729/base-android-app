package com.ww.lp.base.network;

/**
 * 请求数据示例
 * Created by LinkedME06 on 16/11/14.
 */

public class ServerImpTemp {


    /**
     * 普通请求示例
     */
    public void commonTemp() {
//        Map<String, String> params = new HashMap<>();
//        params.put("type", "test110");
//        params.put("postid", "dd");
//        Subscription subscription = ServerImp.getInstance(getApplicationContext())
//                .common("requestTag", Request.Method.GET, ServerInterface.login, params, TestResult.class)
//                .subscribeOn(SchedulerProvider.getInstance().computation())
//                .observeOn(SchedulerProvider.getInstance().ui())
//                .subscribe(new Observer<TestResult>() {
//                    @Override
//                    public void onCompleted() {
//                        //mTaskDetailView.setLoadingIndicator(false);
//                        Log.d("ddd", "onCompleted: ");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("ddd", "onError: ");
//                    }
//
//                    @Override
//                    public void onNext(TestResult loginResult) {
//                        Log.d("ddd", "onNext: ");
//                        //showTask(task);
//                    }
//                });
//        new CompositeSubscription().add(subscription);
    }

    /**
     * 上传文件示例
     */
    public void uploadFile() {
//        Map<String, String> params = new HashMap<>();
//        params.put("type", "test110");
//        params.put("postid", "dd");
//        Map<String, DataPart> fileParams = new HashMap<>();
//        DataPart dataPart = new DataPart("file1.jpg", "abc".getBytes(), "image/jpeg");
//        fileParams.put("type", dataPart);
//        Subscription subscription = ServerImp.getInstance(getApplicationContext())
//                .uploadFile("requestTag", ServerInterface.uploadFile, params, fileParams, TestResult.class)
//                .subscribeOn(SchedulerProvider.getInstance().computation())
//                .observeOn(SchedulerProvider.getInstance().ui())
//                .subscribe(new Observer<TestResult>() {
//                    @Override
//                    public void onCompleted() {
//                        //mTaskDetailView.setLoadingIndicator(false);
//                        Log.d("ddd", "onCompleted: ");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("ddd", "onError: ");
//                    }
//
//                    @Override
//                    public void onNext(TestResult loginResult) {
//                        Log.d("ddd", "onNext: ");
//                        //showTask(task);
//                    }
//                });
//        new CompositeSubscription().add(subscription);
    }
}
