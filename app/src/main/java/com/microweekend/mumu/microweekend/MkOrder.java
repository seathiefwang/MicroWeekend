package com.microweekend.mumu.microweekend;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.microweekend.mumu.microweekend.db.MkHelper;
import com.microweekend.mumu.microweekend.entry.Statuses;
import com.microweekend.mumu.microweekend.event.ResultEvent;
import com.microweekend.mumu.microweekend.event.StatusEvent;
import com.microweekend.mumu.microweekend.util.MkConstants;
import com.microweekend.mumu.microweekend.util.ParseJson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by mumu on 2016/9/10.
 */
public class MkOrder extends WeekendAct implements View.OnClickListener {

    private long content_id;
    private Statuses statuse;
    private TextView tv_payway_zhifubao;
    private TextView tv_payway_weixin;
    private TextView tv_back;
    private TextView tv_title;
    private ImageView imageView;
    private TextView tv_confirm;
    private TextView tv_total;
    private TextView tv_charge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekend_order_pay);
        initView();
    }


    public void initView() {
/*        tv_payway_zhifubao = (TextView)findViewById(R.id.order_payway_zhifubao);
        tv_payway_weixin = (TextView)findViewById(R.id.order_payway_weixin);*/
        tv_back = (TextView)findViewById(R.id.order_pay_back);
        tv_title = (TextView)findViewById(R.id.textView1);
        imageView = (ImageView)findViewById(R.id.imageView);
        tv_confirm = (TextView)findViewById(R.id.textView7);
        tv_total = (TextView)findViewById(R.id.textView6);
        tv_charge = (TextView)findViewById(R.id.textView3);

/*        //设置TextView的drawableLeft的图片大小
        Drawable drawable=getResources().getDrawable(R.drawable.icon_pay_zhifubao);
        drawable.setBounds(0,0,80,80);
        tv_payway_zhifubao.setCompoundDrawables(drawable, null, null, null);
        drawable=getResources().getDrawable(R.drawable.icon_pay_weixin);
        drawable.setBounds(0, 0, 80, 80);
        tv_payway_weixin.setCompoundDrawables(drawable, null, null, null);*/

        tv_back.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);

        initValue();
    }

    public void initValue() {
        Intent intent = getIntent();
        content_id = intent.getLongExtra("content_id", 0);
        ArrayList<Statuses> statuses = ParseJson.ParseStatuses(new MkHelper(context).getContent());
        for (int i=0;i<statuses.size();i++) {
            if (content_id == statuses.get(i).getContent_id()) {
                statuse = statuses.get(i);
                break;
            }
        }

        tv_title.setText(statuse.getContent_title());
        String string = statuse.getPic_path();
        if (!TextUtils.isEmpty(string))MkConstants.ansyLoad.setBitmapOfImageView(string, imageView);
        String chargeType = statuse.getCharge_type();
        if (chargeType.equals("f")) {
            tv_charge.setText("免费");
            tv_total.setText("免费");
        } else if (chargeType.equals("a")) {
            tv_charge.setText("AA制");
            tv_total.setText("AA制");
        } else {
            tv_charge.setText("￥"+statuse.getCharge());
            tv_total.setText("合计：￥"+statuse.getCharge());
        }

    }

    @Override
    public void onEvent(ResultEvent e) {
        if (e.type == StatusEvent.TYPE_CREATEORDER) {
            if (e.getCode() == 1) {
                ToastInfo("订单完成");
                finish();
            } else {
                ToastInfo("添加订单失败-"+e.getMsg());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_pay_back:
                onBackPressed();
                break;
            case R.id.textView7:
                StatusEvent e = new StatusEvent();
                e.type = StatusEvent.TYPE_CREATEORDER;
                e.content_id = content_id;
                EventBus.getDefault().post(e);
                break;
        }
    }
}
