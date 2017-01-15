package com.ww.lp.base.network;

import com.ww.lp.base.components.volleymw.DataPart;

import java.util.Map;

import rx.Observable;
import rx.Single;

/**
 * Created by LinkedME06 on 16/10/28.
 */

public interface ServerApi {

    /**
     * 通用的单次数据请求调用方法
     *
     * @param requestTag 请求标签，用于当前页面销毁时关闭对应标签的请求
     * @param method     请求方式，GET 或者 POST方式
     * @param url        请求地址
     * @param param      请求参数
     * @param clazz      返回的实体类
     * @param <T>        返回的实体类类型
     * @return 服务器返回的结果
     */
    <T> Observable<T> common(String requestTag, int method, String url, Map<String, String> param, Class<T> clazz);
    <T> Single<T> commonSingle(String requestTag, int method, String url, Map<String, String> param, Class<T> clazz);
    /**
     * 通用的单次数据请求调用方法
     *
     * @param requestTag 请求标签，用于当前页面销毁时关闭对应标签的请求
     * @param method     请求方式，GET 或者 POST方式
     * @param url        请求地址
     * @param object     请求参数对象
     * @param clazz      返回的实体类
     * @param <T>        返回的实体类类型
     * @return 服务器返回的结果
     */
    <T> Observable<T> common(String requestTag, int method, String url, Object object, Class<T> clazz);
    <T> Single<T> commonSingle(String requestTag, int method, String url, Object object, Class<T> clazz);
//    Observable<UserInfo> login(String requestTag, @NonNull UserInfo userInfo);

    /**
     * 采用volley上传文件到服务器，不适合上传大文件
     *
     * @param requestTag 请求标签，用于当前页面销毁时关闭对应标签的请求
     * @param url        请求地址
     * @param param      请求参数
     * @param fileParam  上传文件列表参数
     * @param clazz      返回的实体类
     * @param <T>        返回的实体类类型
     * @return 服务器返回的上传结果
     */
    <T> Observable<T> uploadFile(String requestTag, String url, Map<String, String> param, Map<String, DataPart> fileParam, Class<T> clazz);

    /**
     *
     * 采用volley上传文件到服务器，不适合上传大文件
     *
     * @param requestTag 请求标签，用于当前页面销毁时关闭对应标签的请求
     * @param url        请求地址
     * @param headers    请求头
     * @param param      请求参数
     * @param fileParam  上传文件列表参数
     * @param clazz      返回的实体类
     * @param <T>        返回的实体类类型
     * @return 服务器返回的上传结果
     */
    <T> Observable<T> uploadFile(String requestTag, String url, Map<String, String> headers, Map<String, String> param, Map<String, DataPart> fileParam, Class<T> clazz);


}
