package com.microweekend.mumu.microweekend.customui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.microweekend.mumu.microweekend.R;

import java.util.ArrayList;

/**
 * Created by mumu on 2016/10/1.
 */
public class BottomBar extends LinearLayout implements View.OnClickListener {
    private  String TAG = "BottomBar";
    private int curSelected=0;
    private int preSelected=-1;
    private boolean enableChecked=true;
    private OnTabSelectedListener listener;
    private ArrayList<RadioButton> radioButtons;

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        radioButtons = new ArrayList<>();
        LayoutInflater.from(context).inflate(R.layout.bottombar, this);
        RadioButton radioButton = (RadioButton) findViewById(R.id.radioButton0);
        radioButton.setOnClickListener(this);
        radioButtons.add(radioButton);
        radioButton = (RadioButton) findViewById(R.id.radioButton1);
        radioButton.setOnClickListener(this);
        radioButtons.add(radioButton);
        radioButton = (RadioButton) findViewById(R.id.radioButton2);
        radioButton.setOnClickListener(this);
        radioButtons.add(radioButton);
    }

    public void setTabSelectedListener(OnTabSelectedListener listener) {
        this.listener = listener;
    }

    public void setCurSelected(int s) {curSelected = s;}
    public int getCurSelected(){return curSelected;}
    public int getPreSelected() {return preSelected;}
    public void setPreSelected(int s) {preSelected = s;}
    public void setEnableChecked(boolean b) {enableChecked = b;}
    public void setReset() {
        Log.d(TAG, "cur:"+curSelected+"pre:"+preSelected);
        enableChecked = false;
        radioButtons.get(preSelected).setChecked(true);
        curSelected = preSelected;
        preSelected = -1;
        enableChecked = true;
        Log.d(TAG, "cur:"+curSelected+"pre:"+preSelected);
    }


    @Override
    public void onClick(View v) {
        boolean isChecked = ((RadioButton)v).isChecked();
        int select = radioButtons.indexOf(v);
        if (enableChecked && select >= 0 && listener != null) {
            if (isChecked) {
                preSelected = curSelected;curSelected = select;
                if (curSelected == preSelected)
                    listener.onTabReselected(curSelected);
                else
                    listener.onTabSelected(curSelected);
            } else
                listener.onTabUnselected(curSelected);
        }
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int position);
        void onTabUnselected(int position);
        void onTabReselected(int position);
    }
}

