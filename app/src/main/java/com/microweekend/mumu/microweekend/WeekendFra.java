package com.microweekend.mumu.microweekend;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.microweekend.mumu.microweekend.event.ResultEvent;
import com.microweekend.mumu.microweekend.event.StatusEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by mumu on 2016/8/23.
 */
abstract public class WeekendFra extends Fragment {

    protected Context context;
    protected MainTab mainTab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainTab = (MainTab)getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setContext(Context c) {
        context = c;
    }

    public void ToastInfo(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(ResultEvent e) {
        onEvent(e);
    }

    public abstract void onEvent(ResultEvent e);

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
