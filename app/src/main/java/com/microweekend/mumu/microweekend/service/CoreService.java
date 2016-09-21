package com.microweekend.mumu.microweekend.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.microweekend.mumu.microweekend.api.UsersAPI;
import com.microweekend.mumu.microweekend.event.TestEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CoreService extends Service {
    private String TAG = "CoreService";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 相同线程ThreadMode: POSTING，主线程ThreadMode: MAIN，后台线程ThreadMode: BACKGROUND，线程池ThreadMode: ASYNC
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(TestEvent te) {
        Log.d(TAG, "onEvent");
        UsersAPI api = new UsersAPI();
        api.login();
    }
}
