package com.microweekend.mumu.microweekend.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import com.microweekend.mumu.microweekend.api.MkParameters;
import com.microweekend.mumu.microweekend.api.StatusAPI;
import com.microweekend.mumu.microweekend.api.UsersAPI;
import com.microweekend.mumu.microweekend.db.MkHelper;
import com.microweekend.mumu.microweekend.event.MkEvent;
import com.microweekend.mumu.microweekend.event.StatusEvent;
import com.microweekend.mumu.microweekend.event.TestEvent;
import com.microweekend.mumu.microweekend.event.UserEvent;
import com.microweekend.mumu.microweekend.util.MkConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;

public class CoreService extends Service {
    private String TAG = "CoreService";
    private MkHelper db;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        db = new MkHelper(getApplication());
        MkEvent.setDb(db);
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
        return new Binder();
    }

    /**
     * 相同线程ThreadMode: POSTING，主线程ThreadMode: MAIN，后台线程ThreadMode: BACKGROUND，线程池ThreadMode: ASYNC
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onStatusEvent(StatusEvent e) {
        MkParameters param = new MkParameters();
        switch (e.type) {
            case StatusEvent.TYPE_SENDMK:
                param.add("content_title",e.title);
                param.add("content_body",e.body);
                param.add("content_time",e.time);
                param.add("content_address",e.address);
                param.add("latitude",e.latitude);
                param.add("longitude",e.longitude);
                param.add("charge_type",e.charge_type);
                param.add("charge",e.charge);
                param.add("pic", e.getPic());
                new StatusAPI(e).sendMk(param);
                break;
            case StatusEvent.TYPE_TIMELINE:
                param.add("page",e.page);
                param.add("count",e.count);
                new StatusAPI(e).timeLine(param);
                break;
            case StatusEvent.TYPE_UPLOADPIC:
                param.add("pic",e.title);
                new StatusAPI(e).uploadPic(param);
                break;
            case StatusEvent.TYPE_CREATEORDER:
                param.add("content_id",e.content_id);
                new StatusAPI(e).createOrder(param);
                break;
            case StatusEvent.TYPE_GETSENDED:
                param.add("page",e.page);
                param.add("count",e.count);
                new StatusAPI(e).getSended(param);
                break;
            case StatusEvent.TYPE_GETJOINED:
                param.add("page",e.page);
                param.add("count",e.count);
                new StatusAPI(e).getJoined(param);
                break;
            case StatusEvent.TYPE_GETCOLLECT:

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onUserEvent(UserEvent e) {
        MkParameters param = new MkParameters();
        switch (e.type) {
            case UserEvent.TYPE_LOGIN:
                param.add("user_name",e.getUser_name());
                param.add("password",e.getUser_passwd());
                new UsersAPI(e).login(param);
                break;
            case UserEvent.TYPE_REGISTER:
                param.add("user_name",e.getUser_name());
                param.add("password",e.getUser_passwd());
                new UsersAPI(e).register(param);
                break;
            case UserEvent.TYPE_GETUSERINFO:
                new UsersAPI(e).getUserInfo(param);
                break;
            case UserEvent.TYPE_SETNICKNAME:
                param.add("nickname",e.getString());
                new UsersAPI(e).setNickName(param);
                break;
            case UserEvent.TYPE_SETGENDER:
                param.add("user_gender",e.getString());
                new UsersAPI(e).setGender(param);
                break;
            case UserEvent.TYPE_SETDISPLAYPIC:
                param.add("pic",e.getPic());
                new UsersAPI(e).seDisplayPic(param);
                break;
        }
    }
}
