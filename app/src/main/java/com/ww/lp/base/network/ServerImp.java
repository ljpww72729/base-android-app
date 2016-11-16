package com.ww.lp.base.network;

import android.support.annotation.Nullable;
import android.util.Log;

import com.ww.lp.base.components.volleymw.DataPart;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.functions.Func0;

/**
 * Created by LinkedME06 on 16/10/28.
 */

public class ServerImp implements ServerApi {

    @Nullable
    private static ServerImp INSTANCE = null;

    public static ServerImp getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerImp();
        }
        return INSTANCE;
    }


//    @Override
//    public Observable<LoginResult> login(final String requestTag, @NonNull final Student student) {
//        checkNotNull(student);
//        return Observable.defer(new Func0<Observable<LoginResult>>() {
//            @Override
//            public Observable<LoginResult> call() {
//                try {
//                    ServerData<RndResult> serverDataRnd = new ServerData<RndResult>();
//                    Map paramRnd = new HashMap();
//                    paramRnd.put("sid", student.getSid());
//                    return Observable.just(serverDataRnd.getServerData(requestTag, Request.Method.GET, ServerInterface.rnd, paramRnd, RndResult.class))
//                            .concatMap(new Func1<RndResult, Observable<LoginResult>>() {
//                                @Override
//                                public Observable<LoginResult> call(RndResult rndResult) {
//                                    try {
//                                        System.out.println("ddd " + rndResult.getData());
//                                        ServerData<LoginResult> serverDataLogin = new ServerData<LoginResult>();
//                                        Map paramLogin = new HashMap();
//                                        paramLogin.put("sid", student.getSid());
//                                        paramLogin.put("password", DecriptHelper.getEncryptedPassword(student.getPassword(), rndResult.getData()));
//                                        return Observable.just(serverDataLogin.getServerData(requestTag, Request.Method.POST, ServerInterface.login, paramLogin, LoginResult.class));
//                                    } catch (ExecutionException | InterruptedException e) {
//                                        e.printStackTrace();
//                                        return Observable.error(e);
//                                    }
//                                }
//                            });
//                } catch (InterruptedException | ExecutionException e) {
//                    Log.e("routes", e.getMessage());
//                    return Observable.error(e);
//                }
//            }
//        });
//    }

    @Override
    public <T> Observable<T> common(final String requestTag, final int method, final String url, final Map<String, String> param, final Class<T> clazz) {
        return Observable.defer(new Func0<Observable<T>>() {
            @Override
            public Observable<T> call() {
                try {
                    ServerData<T> serverData = new ServerData<T>();
                    return Observable.just(serverData.getServerData(requestTag, method, url, param, clazz));
                } catch (InterruptedException | ExecutionException e) {
                    Log.e("routes", e.getMessage());
                    return Observable.error(e);
                }
            }
        });
    }
    @Override
    public <T> Observable<T> common(final String requestTag, final int method, final String url, final Object object, final Class<T> clazz) {
        return Observable.defer(new Func0<Observable<T>>() {
            @Override
            public Observable<T> call() {
                try {
                    // TODO: 16/11/17 测试以下方式是否好用
                    Map<String, String> param = new HashMap<String, String>();
                    Field[] fields = object.getClass().getDeclaredFields();
                    for (int i = 0; i < fields.length; i++){
                        try {
                            param.put(fields[i].getName(), String.valueOf(fields[i].get(object)));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    ServerData<T> serverData = new ServerData<T>();
                    return Observable.just(serverData.getServerData(requestTag, method, url, param, clazz));
                } catch (InterruptedException | ExecutionException e) {
                    Log.e("routes", e.getMessage());
                    return Observable.error(e);
                }
            }
        });
    }

    @Override
    public <T> Observable<T> uploadFile(final String requestTag, final String url, final Map<String, String> param, final Map<String, DataPart> fileParam, final Class<T> clazz) {
        return uploadFile(requestTag, url, null, param, fileParam, clazz);
    }

    @Override
    public <T> Observable<T> uploadFile(final String requestTag, final String url, final Map<String, String> headers, final Map<String, String> param, final Map<String, DataPart> fileParam, final Class<T> clazz) {
        return Observable.defer(new Func0<Observable<T>>() {
            @Override
            public Observable<T> call() {
                try {
                    // TODO: 16/11/17 完善以下请求
                    ServerData<T> serverData = new ServerData<T>();

//                    return Observable.just(Compressor.getDefault(this)
//                            .compressToFileAsObservable(actualImage)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread()))
//                            .concatMap(new Func1<File, Observable<T>>() {
//                                @Override
//                                public Observable<T> call(File file) {
//                                    try {
//                                        ServerData<T> serverDataUpload = new ServerData<T>();
//                                        Map paramLogin = new HashMap();
//                                        paramLogin.put("sid", student.getSid());
//                                        paramLogin.put("password", DecriptHelper.getEncryptedPassword(student.getPassword(), rndResult.getData()));
//                                        return Observable.just(serverDataUpload.uploadFile(requestTag, url, headers, param, fileParam, clazz));
//                                    } catch (ExecutionException | InterruptedException e) {
//                                        e.printStackTrace();
//                                        return Observable.error(e);
//                                    }
//                                }
//                            });

                    return Observable.just(serverData.uploadFile(requestTag, url, headers, param, fileParam, clazz));
                } catch (InterruptedException | ExecutionException e) {
                    Log.e("routes", e.getMessage());
                    return Observable.error(e);
                }
            }
        });
    }

}
