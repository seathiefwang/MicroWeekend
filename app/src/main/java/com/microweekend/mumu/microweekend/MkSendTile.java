package com.microweekend.mumu.microweekend;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.microweekend.mumu.microweekend.event.ResultEvent;
import java.util.Calendar;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

/**
 * Created by mumu on 2016/9/10.
 */
public class MkSendTile extends WeekendAct implements View.OnClickListener, OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    public static final String KEY_TITLE = "title";
    public static final String KEY_TIME = "time";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_CHARGE_TYPE = "charge_type";
    public static final String KEY_CHARGE = "charge";

    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";

    private EditText et_title;
    private TextView tv_date;
    private TextView tv_time;
    private TextView tv_address;

    private String title;
    private String time;
    private String address;
    private Double latitude;
    private Double longitude;
    private String charge_type="f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekend_send_title);

        initView();
    }

    public void initView() {
        et_title = (EditText)findViewById(R.id.editText);
        tv_date = (TextView)findViewById(R.id.textView_datepicker);
        tv_time = (TextView)findViewById(R.id.textView_timepicker);
        tv_address = (TextView)findViewById(R.id.textView);
        RadioButton rb_free = (RadioButton) findViewById(R.id.radioButton);
        RadioButton rb_aa = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton rb_pay = (RadioButton) findViewById(R.id.radioButton2);

        TextView tv_next = (TextView) findViewById(R.id.send_title_next);
        TextView tv_back = (TextView) findViewById(R.id.send_title_cancel);

        rb_free.setOnClickListener(this);
        rb_aa.setOnClickListener(this);
        rb_pay.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        tv_address.setOnClickListener(this);
        rb_pay.setEnabled(false);

        dateTimePicker();
    }

    public void dateTimePicker() {
        final Calendar calendar = Calendar.getInstance();

        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);
        tv_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerDialog.setVibrate(false);
                datePickerDialog.setYearRange(1985, 2028);
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG);
            }
        });

        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.setVibrate(false);
                timePickerDialog.setCloseOnSingleTapMinute(false);
                timePickerDialog.show(getFragmentManager(), TIMEPICKER_TAG);
            }
        });
    }

    @Override
    public void onEvent(ResultEvent e) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_title_next:
                nextStep();
                break;
            case R.id.send_title_cancel:
                onBackPressed();
                break;
            case R.id.radioButton:
                charge_type = "f";
                break;
            case R.id.radioButton1:
                charge_type = "a";
                break;
            case R.id.radioButton2:
                ToastInfo("未开放权限");
                break;
            case R.id.textView:
                startActivityForResult(new Intent(this, MapAct.class),MapAct.REQUEST_CODE);
                break;
        }
    }

    public void nextStep() {
        title = et_title.getText().toString();
        if (title.equals("")) {
            ToastInfo("请输入活动标题");return;
        }
        String d = tv_date.getText().toString();
        String t = tv_time.getText().toString();
        if (d.equals("")) {
            ToastInfo("请选择活动日期");return;
        }
        if (!t.equals("")) {
            time = d + "—" + t;
        } else {
            time = d;
        }

        if (TextUtils.isEmpty(address)) {
            ToastInfo("请选择活动地点");return;
        }

        Intent intent = new Intent(MkSendTile.this,MkSendDetail.class);
        startActivityForResult(intent, MkSendDetail.REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == MkSendDetail.REQUEST_CODE) {
            data.putExtra(KEY_TITLE, title);
            data.putExtra(KEY_TIME, time);
            data.putExtra(KEY_ADDRESS, address);
            data.putExtra(KEY_LATITUDE, latitude);
            data.putExtra(KEY_LONGITUDE, longitude);
            data.putExtra(KEY_CHARGE_TYPE, charge_type);
            data.putExtra(KEY_CHARGE, 0);
                /*
                 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，这样就可以在onActivityResult方法中得到Intent对象，
                 */
            setResult(RESULT_OK, data);
            //    结束当前这个Activity对象的生命
            finish();
        } else if (resultCode == RESULT_OK && requestCode == MapAct.REQUEST_CODE) {
            latitude = data.getDoubleExtra("latitude", 39.86923);
            longitude = data.getDoubleExtra("longitude", 116.397428);
            address = data.getStringExtra("address");
            tv_address.setText(address);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        tv_date.setText(year + "年" + month + "月" + day + "日");
        //ToastInfo("new date:" + year + "-" + month + "-" + day);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        tv_time.setText(hourOfDay + "时" + minute + "分");
        //ToastInfo("new time:" + hourOfDay + "-" + minute);
    }
}
