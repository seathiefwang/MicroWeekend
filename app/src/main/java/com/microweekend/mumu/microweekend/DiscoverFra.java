package com.microweekend.mumu.microweekend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.microweekend.mumu.microweekend.event.ResultEvent;

/**
 * Created by mumu on 2016/9/11.
 */
public class DiscoverFra extends WeekendFra {

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.discover_lay,null);
        return view;
    }

    @Override
    public void onEvent(ResultEvent e) {

    }
}
