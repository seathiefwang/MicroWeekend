package com.microweekend.mumu.microweekend.api;

import android.util.Log;

import com.microweekend.mumu.microweekend.net.HttpManager;
/**
 * Created by mumu on 2016/9/20.
 */
public abstract class MkAPI {
    /**
     * 访问服务器的api地址
     */
    public static final String API_SERVER = "http://192.168.0.103/api.php";

    /**
     * post请求方式
     */
    public static final String HTTPMETHOD_POST = "POST";

    /**
     * get请求方式
     */
    public static final String HTTPMETHOD_GET = "GET";


    public MkAPI() {

    }

    protected void request(String url, MkParameters params, String httpMethod) {
        String e = HttpManager.openUrl(url, httpMethod, params, params.getValue("pic"));
        Log.i("MkAPI","test:"+e);
    }
}
