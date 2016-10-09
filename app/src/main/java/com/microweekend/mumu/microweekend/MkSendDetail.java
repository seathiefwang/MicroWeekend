package com.microweekend.mumu.microweekend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.microweekend.mumu.microweekend.event.ResultEvent;
import com.microweekend.mumu.microweekend.event.StatusEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by mumu on 2016/9/10.
 */
public class MkSendDetail extends WeekendAct implements View.OnClickListener {

    public static final int REQUEST_CODE = 234;
    public static final String KEY_BODY = "body";
    private String title;
    private String time;
    private String address;
    private String pay;


    private ImageView iv_back;
    private TextView tv_send;
    private EditText et_body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekend_send_detail);

        initView();
    }

    public void initView() {

        iv_back = (ImageView)findViewById(R.id.back);
        tv_send = (TextView)findViewById(R.id.send_detail_send);
        et_body = (EditText)findViewById(R.id.editText_content);

        iv_back.setOnClickListener(this);
        tv_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.send_detail_send:
                Intent intent = new Intent();

                intent.putExtra(KEY_BODY, et_body.getText().toString());
                /*
                 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，这样就可以在onActivityResult方法中得到Intent对象，
                 */
                setResult(RESULT_OK, intent);
                //    结束当前这个Activity对象的生命
                finish();
                break;
        }
    }

    @Override
    public void onEvent(ResultEvent e) {

    }
}
