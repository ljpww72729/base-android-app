package com.ww.lp.base.components.volleymw;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.orhanobut.logger.Logger;
import com.ww.lp.base.data.ErrorResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lipeng on 2016 6-16.
 */
public class GsonRequest<T> extends Request<T> {
    private Map<String, String> param;
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;


    /**
     * 默认的get方法请求
     *
     * @see #GsonRequest(int, String, Map, Class, Map, Response.Listener, Response.ErrorListener)
     */
    public GsonRequest(String url, Map<String, String> param, Class<T> clazz,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(url, param, clazz, null, listener, errorListener);
    }

    /**
     * @param method 传入请求方法
     * @see #GsonRequest(int, String, Map, Class, Map, Response.Listener, Response.ErrorListener)
     */
    public GsonRequest(int method, String url, Map<String, String> param, Class<T> clazz,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(method, url, param, clazz, null, listener, errorListener);
    }

    public GsonRequest(String url, Map<String, String> param, Class<T> clazz, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, param, clazz, headers, listener, errorListener);
    }

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url     URL of the request to make
     * @param clazz   Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest(int method, String url, Map<String, String> param, Class<T> clazz, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.param = removeNullAndEmpty(param);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
    }

    /**
     * 去除map中NULL或者空字符串的数据项
     *
     * @param param 参数键值对
     * @return
     */
    private Map<String, String> removeNullAndEmpty(Map<String, String> param) {
        if (param == null){
            return null;
        }
        Map<String,String> formatParam = new HashMap<>();
        Set<Map.Entry<String, String>> entry = param.entrySet();
        for (Map.Entry<String, String> format : entry) {
            if (!TextUtils.isEmpty(format.getValue())) {
                formatParam.put(format.getKey(), format.getValue());
            }
        }
        return formatParam;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        Gson gson = new Gson();
        String json = null;
        try {
            json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Logger.json(json);
            return Response.success(gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (JsonSyntaxException e){
            // TODO: 16/11/27 此处有待优化，如何更顺滑的体验
            return Response.error(new VolleyError(gson.fromJson(json, ErrorResult.class).getData().getErr_msg()));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new VolleyError(gson.fromJson(json, ErrorResult.class).getData().getErr_msg()));
//            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Logger.d("Server Request Params --> ", param);
        return param;
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=" + getParamsEncoding();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        Map<String, String> params = getParams();
        if (params != null && params.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            for (Map.Entry<String, String> entry:params.entrySet()) {
                try {
                    // TODO: 16/11/24 并没有进行encode编码
                    // TODO: 16/11/28 特别argly的地方
                    if (!TextUtils.equals(entry.getKey(), "imgs")){
                        jsonObject.put(entry.getKey(), entry.getValue());
                    }else{
                        jsonObject.put(entry.getKey(), new JSONArray(entry.getValue()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            try {
                return jsonObject.toString().getBytes(getParamsEncoding());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
