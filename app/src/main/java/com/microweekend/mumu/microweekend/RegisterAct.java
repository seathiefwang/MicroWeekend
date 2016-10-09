package com.microweekend.mumu.microweekend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.microweekend.mumu.microweekend.event.ResultEvent;
import com.microweekend.mumu.microweekend.event.UserEvent;
import com.microweekend.mumu.microweekend.util.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by mumu on 2016/9/29.
 */
public class RegisterAct extends Activity implements View.OnClickListener {
    private EditText et_account;
    private EditText et_password;
    private Button bt_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekend_register);
        initView();
    }

    public void initView() {
        et_account = (EditText)findViewById(R.id.accountEt);
        et_password = (EditText)findViewById(R.id.pwdEt);
        bt_submit = (Button)findViewById(R.id.subBtn);

        bt_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.subBtn:
                register();
                break;
        }
    }

    public void register() {
        String name = et_account.getText().toString();
        String pwd = et_password.getText().toString();
        if (!Utility.isMobileNO(name)) {
            Utility.showToast("手机格式不正确", this);return;
        }
        if (pwd.length() < 6) {
            Utility.showToast("密码长度不能小于6位", this);return;
        }

        UserEvent e = new UserEvent();
        e.type = e.TYPE_REGISTER;
        e.setUser_name(name);
        e.setUser_passwd(pwd);
        EventBus.getDefault().post(e);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ResultEvent e) {
        switch (e.type) {
            case UserEvent.TYPE_REGISTER:
                if (e.getCode() == 1) {
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, (String)e.getMsg(), Toast.LENGTH_SHORT).show();
                }
        }
    }
}
