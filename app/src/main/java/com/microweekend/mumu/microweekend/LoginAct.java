package com.microweekend.mumu.microweekend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.microweekend.mumu.microweekend.entry.Statuses;
import com.microweekend.mumu.microweekend.event.ResultEvent;
import com.microweekend.mumu.microweekend.event.StatusEvent;
import com.microweekend.mumu.microweekend.event.UserEvent;
import com.microweekend.mumu.microweekend.util.MkConstants;
import com.microweekend.mumu.microweekend.util.Myspf;
import com.microweekend.mumu.microweekend.util.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by mumu on 2016/9/29.
 */
public class LoginAct extends Activity implements View.OnClickListener {
    private EditText et_account;
    private EditText et_password;
    private Button bt_submit;
    private TextView tv_register;
    private String user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekend_login);
        initView();
    }

    public void initView() {
        et_account = (EditText)findViewById(R.id.accountEt);
        et_password = (EditText)findViewById(R.id.pwdEt);
        bt_submit = (Button)findViewById(R.id.subBtn);
        tv_register = (TextView)findViewById(R.id.tv_register);

        bt_submit.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.subBtn:
                login();
                break;
            case R.id.tv_register:
                startActivity(new Intent(this,RegisterAct.class));
                break;
        }
    }

    public void login() {
        user_name = et_account.getText().toString();
        String pwd = et_password.getText().toString();
/*        if (!Utility.isMobileNO(user_name)) {
            Utility.showToast("手机格式不正确", this);return;
        }
        if (pwd.length() < 6) {
            Utility.showToast("密码长度不能小于6位", this);return;
        }*/

        UserEvent e = new UserEvent();
        e.type = e.TYPE_LOGIN;
        e.setUser_name(user_name);
        e.setUser_passwd(pwd);
        EventBus.getDefault().post(e);
    }

    public void loginConfirm(ResultEvent e) {
        Myspf.saveUserName(this, user_name);
        MkConstants.USER_NAME = user_name;
        Myspf.saveUUID(this, (String) e.getMsg());
        MkConstants.UUID = (String) e.getMsg();
        Myspf.saveLoginFlag(this, true);
        //get user info
        UserEvent ue = new UserEvent();
        ue.type = UserEvent.TYPE_GETUSERINFO;
        EventBus.getDefault().post(ue);

        startActivity(new Intent(this, MainTab.class));
        finish();
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
            case UserEvent.TYPE_LOGIN:
                if (e.getCode() == 1) {
                    loginConfirm(e);
                } else {
                    Toast.makeText(this, (String)e.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
