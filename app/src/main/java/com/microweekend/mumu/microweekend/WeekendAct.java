package com.microweekend.mumu.microweekend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.microweekend.mumu.microweekend.adapter.AnsyLoad;
import com.microweekend.mumu.microweekend.adapter.AnsyLoadImg;
import com.microweekend.mumu.microweekend.db.MkHelper;
import com.microweekend.mumu.microweekend.event.ResultEvent;
import com.microweekend.mumu.microweekend.util.HttpUtil;
import com.microweekend.mumu.microweekend.util.MkConstants;
import com.microweekend.mumu.microweekend.util.Myspf;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by mumu on 2016/8/14.
 */
abstract public class WeekendAct extends Activity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        if (!HttpUtil.NetISok(context)) {
            ToastInfo("网络连接异常");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(ResultEvent e) {
        if (e.getCode() == 10010) {
            Log.i("MkAPI", "uuid:" + MkConstants.UUID);
            Myspf.saveLoginFlag(this, false);
            new MkHelper(this).delete();//清空数据库
            Intent intent = new Intent(this, LoginAct.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);  //Intent.FLAG_ACTIVITY_CLEAR_TOP，这样开启B时将会清除该进程空间的所有Activity
            startActivity(intent);
        } else {
            onEvent(e);
        }
    }
    public abstract void onEvent(ResultEvent e);

    public void ToastInfo(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
