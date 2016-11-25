package com.ww.lp.base.network;

import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.VolleySingleton;
import com.ww.lp.base.components.volleymw.DataPart;
import com.ww.lp.base.components.volleymw.GsonRequest;
import com.ww.lp.base.components.volleymw.VolleyMultipartRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 通过volley请求数据并转化为对象
 * Created by LinkedME06 on 16/10/28.
 */

public class ServerData<T> {


    public ServerData(){}

    protected T getServerData(String requestTag, int method, String url, Map<String, String> param, Class<T> clazz) throws ExecutionException, InterruptedException {
        RequestFuture<T> future = RequestFuture.newFuture();
        if (method == Request.Method.GET && param != null && !param.isEmpty()){
            url += addParameters(param);
            param.clear();
        }
        GsonRequest<T> gsonRequest = new GsonRequest<>(method, url, param, clazz, future, future);
        gsonRequest.setTag(requestTag);
        //添加请求任务
        VolleySingleton.getInstance().addToRequestQueue(gsonRequest);
        return future.get();
    }

    protected T uploadFile(String requestTag, String url, Map<String, String> headers, Map<String, String> param, Map<String, DataPart> fileParam, Class<T> clazz) throws ExecutionException, InterruptedException {
        RequestFuture<T> future = RequestFuture.newFuture();
        VolleyMultipartRequest<T> volleyMultipartRequest = new VolleyMultipartRequest<>(url, headers, param, fileParam, clazz, future, future);
        volleyMultipartRequest.setTag(requestTag);
        //添加请求任务
        VolleySingleton.getInstance().addToRequestQueue(volleyMultipartRequest);
        return future.get();
    }

    /**
     * 构建get请求
     * @param params 请求参数
     * @return 拼接后的get请求串
     */
    private String addParameters(Map<String, String> params) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                encodedParams.append('&');
            }
            return "?" + encodedParams.toString().substring(0, encodedParams.length() - 1);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + "UTF-8", uee);
        }
    }

}
