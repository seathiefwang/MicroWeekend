package com.microweekend.mumu.microweekend;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.microweekend.mumu.microweekend.event.ResultEvent;
import com.microweekend.mumu.microweekend.service.CoreService;
import com.microweekend.mumu.microweekend.util.MkConstants;
import com.microweekend.mumu.microweekend.util.Myspf;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WelcomeAct extends AppCompatActivity {

    private ServiceConnection coreConnection;
    TextView tv_hello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    public void initView() {
        tv_hello = (TextView)findViewById(R.id.hello);
        coreConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                initValue();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
    }

    public void initValue() {
        if (Myspf.getFirstFlag(this)) {
            Myspf.saveFirstFlag(this);
        }
        if (Myspf.getLoginFlag(this)) {
            MkConstants.USER_NAME = Myspf.getUserName(this);
            MkConstants.UUID = Myspf.getUUID(this);
            startActivity(new Intent(WelcomeAct.this, MainTab.class));
        } else {
            startActivity(new Intent(WelcomeAct.this, LoginAct.class));
        }
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ResultEvent e) {
        switch (e.type) {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(getApplicationContext(), CoreService.class);
        startService(intent);
        bindService(intent, coreConnection, BIND_AUTO_CREATE);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(coreConnection);
        EventBus.getDefault().unregister(this);
    }
}
