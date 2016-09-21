package com.microweekend.mumu.microweekend;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.microweekend.mumu.microweekend.util.HttpUtil;

/**
 * Created by mumu on 2016/8/14.
 */
public class WeekendAct extends Activity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        if (!HttpUtil.NetISok(context)) {
            ToastInfo("网络连接异常");
        }
    }

    public void ToastInfo(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
