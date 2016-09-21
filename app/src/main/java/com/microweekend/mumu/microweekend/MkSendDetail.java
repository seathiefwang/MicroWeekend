package com.microweekend.mumu.microweekend;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mumu on 2016/9/10.
 */
public class MkSendDetail extends WeekendAct implements View.OnClickListener {

    private TextView tv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekend_send_detail);

        initView();
    }

    public void initView() {
        tv_back = (TextView)findViewById(R.id.send_detail_back);

        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_detail_back:
                onBackPressed();
                break;
        }
    }
}
