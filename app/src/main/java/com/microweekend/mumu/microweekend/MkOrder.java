package com.microweekend.mumu.microweekend;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mumu on 2016/9/10.
 */
public class MkOrder extends WeekendAct implements View.OnClickListener {

    private TextView tv_payway_zhifubao;
    private TextView tv_payway_weixin;
    private TextView tv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekend_order_pay);
        initView();
    }

    public void initView() {
        tv_payway_zhifubao = (TextView)findViewById(R.id.order_payway_zhifubao);
        tv_payway_weixin = (TextView)findViewById(R.id.order_payway_weixin);
        tv_back = (TextView)findViewById(R.id.order_pay_back);

        //设置TextView的drawableLeft的图片大小
        Drawable drawable=getResources().getDrawable(R.drawable.icon_pay_zhifubao);
        drawable.setBounds(0,0,80,80);
        tv_payway_zhifubao.setCompoundDrawables(drawable, null, null, null);
        drawable=getResources().getDrawable(R.drawable.icon_pay_weixin);
        drawable.setBounds(0,0,80,80);
        tv_payway_weixin.setCompoundDrawables(drawable, null, null, null);

        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_pay_back:
                onBackPressed();
                break;
        }
    }
}
