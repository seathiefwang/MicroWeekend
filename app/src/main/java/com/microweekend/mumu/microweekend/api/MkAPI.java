package com.microweekend.mumu.microweekend.api;

import android.util.Log;

import com.microweekend.mumu.microweekend.event.MkEvent;
import com.microweekend.mumu.microweekend.event.ResultEvent;
import com.microweekend.mumu.microweekend.net.HttpManager;
import com.microweekend.mumu.microweekend.util.MkConstants;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by mumu on 2016/9/20.
 */
public abstract class MkAPI {
    /**
     * 访问服务器的api地址
     */
    public static final String API_SERVER = "http://112.74.60.189/api.php";

    /**
     * post请求方式
     */
    public static final String HTTPMETHOD_POST = "POST";

    /**
     * get请求方式
     */
    public static final String HTTPMETHOD_GET = "GET";

    private MkEvent me;

    public MkAPI(MkEvent e) {
        me = e;
    }

    protected void request(String url, MkParameters params, String httpMethod) {
        params.add("app","micro_weekend");
        params.add("uuid", MkConstants.UUID);

        String s = HttpManager.openUrl(url, httpMethod, params, params.getValue("pic"));
        Log.i("MkAPI", "recv:" + s);
        ResultEvent e = new ResultEvent();
        e.type = me.type;
        e.setJson(s);
        EventBus.getDefault().post(e);
    }
    protected String requestForResult(String url, MkParameters params, String httpMethod) {
        params.add("app","micro_weekend");
        params.add("uuid", MkConstants.UUID);

        return HttpManager.openUrl(url, httpMethod, params, params.getValue("pic"));
    }
}
