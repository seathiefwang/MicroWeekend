package com.microweekend.mumu.microweekend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mumu on 2016/9/10.
 */
public class MkSendTile extends WeekendAct implements View.OnClickListener {

    private TextView tv_next;
    private TextView tv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekend_send_title);

        initView();
    }

    public void initView() {
        tv_next = (TextView)findViewById(R.id.send_title_next);
        tv_back = (TextView)findViewById(R.id.send_title_cancel);

        tv_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_title_next:
                startActivity(new Intent(MkSendTile.this,MkSendDetail.class));
                break;
            case R.id.send_title_cancel:
                onBackPressed();
                break;
        }
    }
}
