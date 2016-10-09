package com.microweekend.mumu.microweekend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.microweekend.mumu.microweekend.customui.CircleImageView;
import com.microweekend.mumu.microweekend.db.MkHelper;
import com.microweekend.mumu.microweekend.entry.Statuses;
import com.microweekend.mumu.microweekend.event.ResultEvent;
import com.microweekend.mumu.microweekend.util.MkConstants;
import com.microweekend.mumu.microweekend.util.ParseJson;

import java.util.ArrayList;

/**
 * Created by mumu on 2016/9/9.
 */
public class MkDetail extends WeekendAct implements View.OnClickListener {

    private long content_id;
    private Statuses statuse;
    private ArrayList<String> listDetail;
    private ListView lv_detail;
    private ImageView iv_back;
    private TextView tv_head;
    private TextView tv_join;
    private TextView tv_charge;
    private View head_view;
    private DetailAdapter dadapter;
    private String TAG = "MkDetail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekend_detail_layout);
        initView();
    }

    public void initView() {
        lv_detail = (ListView)findViewById(R.id.weekend_detaillistview);
        iv_back = (ImageView)findViewById(R.id.toolbar_back);
        tv_head = (TextView)findViewById(R.id.username);
        tv_join = (TextView)findViewById(R.id.tv_join);
        tv_charge = (TextView)findViewById(R.id.tv_charge);
        head_view = getLayoutInflater().inflate(R.layout.weekend_detail_headview,null);
        TextView tv_detail = (TextView)head_view.findViewById(R.id.textView7);
        TextView tv_comment = (TextView)head_view.findViewById(R.id.textView8);
        TextView tv_like = (TextView)head_view.findViewById(R.id.textView9);


        tv_like.setOnClickListener(this);
        tv_comment.setOnClickListener(this);
        tv_detail.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_head.setOnClickListener(this);
        tv_join.setOnClickListener(this);

        lv_detail.addHeaderView(head_view);

        initValue();
    }

    public void initValue(){
        Intent intent = getIntent();
        content_id = intent.getLongExtra("content_id",0);
        ArrayList<Statuses> statuses = ParseJson.ParseStatuses(new MkHelper(context).getContent());
        for (int i=0;i<statuses.size();i++) {
            if (content_id == statuses.get(i).getContent_id()) {
                statuse = statuses.get(i);
                break;
            }
        }
        MkConstants.ansyLoad.setBitmapOfImageView(statuse.getDisplayPic(), (CircleImageView) head_view.findViewById(R.id.circleImageView));//displaypic
        ((TextView)head_view.findViewById(R.id.user_name)).setText(statuse.getUserNick());      //nickname
        ((TextView)head_view.findViewById(R.id.textView3)).setText(statuse.getContent_title());//title
        ((TextView)head_view.findViewById(R.id.textView4)).setText(statuse.getContent_time());//time
        TextView tv_address = (TextView)head_view.findViewById(R.id.textView5);
        tv_address.setText(statuse.getContent_address());//address
        tv_address.setOnClickListener(this);

        String charge_type,string = statuse.getCharge_type();

        if (string.equals("f"))
            charge_type="免费";
        else if (string.equals("a"))
            charge_type="AA制";
        else if (string.equals("p"))
            charge_type="￥"+statuse.getCharge();
        else
            charge_type="未知";

        ((TextView)head_view.findViewById(R.id.textView6)).setText(charge_type);//pay
        MkConstants.ansyLoad.setBitmapOfImageView(statuse.getPic_path(), (ImageView) head_view.findViewById(R.id.imageView5));

        tv_charge.setText(charge_type);
        listDetail = new ArrayList<>();
        listDetail.add(statuse.getContent_body());
        dadapter = new DetailAdapter(context, listDetail);
        lv_detail.setAdapter(dadapter);
    }

    public void onEvent(ResultEvent e) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           case R.id.textView7:
                dadapter.setLayoutview(0);
                dadapter.notifyDataSetChanged();
                break;
            case R.id.textView8:
                dadapter.setLayoutview(1);
                dadapter.notifyDataSetChanged();
                break;
            case R.id.textView9:
                dadapter.setLayoutview(2);
                dadapter.notifyDataSetChanged();
                break;
            case R.id.textView5:
                Intent intent = new Intent(this, MapAct.class);
                intent.putExtra("is_setmarker", false);
                intent.putExtra("latitude", statuse.getLatitude());
                intent.putExtra("longitude", statuse.getLongitude());
                startActivity(intent);
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.username:
                lv_detail.smoothScrollToPosition(0);
                break;
            case R.id.tv_join:
                intent = new Intent(MkDetail.this, MkOrder.class);
                intent.putExtra("content_id", content_id);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class DetailAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private int layoutview = 0;
        private ArrayList<String> listDetail;
        public DetailAdapter(Context c, ArrayList<String>ld) {
            inflater = LayoutInflater.from(c);
            listDetail = ld;
        }
        @Override
        public int getCount() {
            return listDetail.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i(TAG, "getView"+position);
                if (layoutview == 0) {
                    convertView = inflater.inflate(R.layout.weekend_detail_detailview, null);
                    if (!initDetailView(position, convertView)) return null;
                }else if (layoutview == 1) {
                    convertView = inflater.inflate(R.layout.weekend_detail_commentsview, null);
                } else {
                    convertView = inflater.inflate(R.layout.weekend_detail_likeview, null);
                }
            return convertView;
        }

        private boolean initDetailView(int position, View v) {
            String body = listDetail.get(position);
            if (body == null) return false;
            ((TextView)v.findViewById(R.id.textView9)).setText(body);
            return true;
        }

        public void setLayoutview(int i) {layoutview = i;}
    }
}
