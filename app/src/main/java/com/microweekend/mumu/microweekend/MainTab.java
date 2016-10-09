package com.microweekend.mumu.microweekend;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.microweekend.mumu.microweekend.customui.BottomBar;
import com.microweekend.mumu.microweekend.event.ResultEvent;
import com.microweekend.mumu.microweekend.event.StatusEvent;
import com.microweekend.mumu.microweekend.event.UserEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;

public class MainTab extends WeekendAct {
    private BottomBar bottomNavigationBar;
    private FragmentManager fmanager;
    private HomeTimeLine fra_home;
    private DiscoverFra fra_discover;
    private MeFra fra_me;
    private MessageFra fra_message;
    private ArrayList<String> photos;
    private Context context;
    private String TAG = "MainTab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintabs);
        context = this;
        initView();
    }

    /**
     *
     */
    public void initView() {
        bottomNavigationBar = (BottomBar) findViewById(R.id.bottom_bar);
        setDefaultFragment();
        onClick();
    }

    public void setDefaultFragment() {
        fmanager = getFragmentManager();
        FragmentTransaction ft = fmanager.beginTransaction();
        fra_home = new HomeTimeLine();
        fra_home.setContext(context);
        ft.replace(R.id.fl_content, fra_home);
        ft.commit();
    }

    public void onClick() {
        bottomNavigationBar.setTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                FragmentTransaction ft = fmanager.beginTransaction();
                switch (position) {
                    case 0:
                        if (fra_home == null) {
                            fra_home = new HomeTimeLine();
                            fra_home.setContext(context);
                        }
                        ft.replace(R.id.fl_content, fra_home);
                        break;
                    case 9:
                        if (fra_discover == null) {
                            fra_discover = new DiscoverFra();
                            fra_discover.setContext(context);
                        }
                        ft.replace(R.id.fl_content, fra_discover);
                        break;
                    case 1:
                        bottomNavigationBar.setReset();
                        PhotoPicker.builder()
                                .setPhotoCount(1)
                                .setShowCamera(false)
                                .setShowGif(false)
                                .setPreviewEnabled(true)
                                .start(MainTab.this, PhotoPicker.REQUEST_CODE);
                        break;
                    case 8:
                        if (fra_message == null) {
                            fra_message = new MessageFra();
                            fra_message.setContext(context);
                        }
                        ft.replace(R.id.fl_content, fra_message);
                        break;
                    case 2:
                        if (fra_me == null) {
                            fra_me = new MeFra();
                            fra_me.setContext(context);
                        }
                        ft.replace(R.id.fl_content, fra_me);
                        break;
                    default:
                        break;
                }
                ft.commit();
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
            if (requestCode == PhotoPicker.REQUEST_CODE && data != null) {
                photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                Log.d(TAG, photos.get(0));
                startActivityForResult(new Intent(MainTab.this, MkSendTile.class), MkSendDetail.REQUEST_CODE);
            } else if (requestCode == MkSendDetail.REQUEST_CODE && data != null) {
                StatusEvent e = new StatusEvent();
                e.type = StatusEvent.TYPE_SENDMK;
                e.setPic(photos.get(0));
                e.body = data.getStringExtra(MkSendDetail.KEY_BODY);
                e.title = data.getStringExtra(MkSendTile.KEY_TITLE);
                e.time = data.getStringExtra(MkSendTile.KEY_TIME);
                e.address = data.getStringExtra(MkSendTile.KEY_ADDRESS);
                e.latitude = data.getDoubleExtra(MkSendTile.KEY_LATITUDE, 0.0);
                e.longitude = data.getDoubleExtra(MkSendTile.KEY_LONGITUDE, 0.0);
                e.charge_type = data.getStringExtra(MkSendTile.KEY_CHARGE_TYPE);
                e.charge = data.getIntExtra(MkSendTile.KEY_CHARGE, 0);
                EventBus.getDefault().post(e);
            } else if (requestCode == MeFra.REQUEST_CODE && data != null) {
                UserEvent e = new UserEvent();
                e.type = UserEvent.TYPE_SETDISPLAYPIC;
                e.setPic(data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS).get(0));
                EventBus.getDefault().post(e);
            }
    }

    @Override
    public void onEvent(ResultEvent e) {
    }
}