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

import java.util.zip.Inflater;

/**
 * Created by mumu on 2016/9/9.
 */
public class MkDetail extends WeekendAct implements View.OnClickListener {

    private ListView lv_detail;
    private ImageView iv_back;
    private TextView tv_head;
    private TextView tv_join;
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
        dadapter = new DetailAdapter(context);
        lv_detail.setAdapter(dadapter);
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
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.username:
                lv_detail.smoothScrollToPosition(0);
                break;
            case R.id.tv_join:
                startActivity(new Intent(MkDetail.this,MkOrder.class));
                break;
        }
    }

    private class DetailAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private int layoutview = 0;
        public DetailAdapter(Context c) {
            inflater = LayoutInflater.from(c);
        }
        @Override
        public int getCount() {
            return 10;
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
            if (position < 10) {
                if (layoutview == 0) {
                    convertView = inflater.inflate(R.layout.weekend_detail_detailview, null);
                }else if (layoutview == 1) {
                    convertView = inflater.inflate(R.layout.weekend_detail_commentsview, null);
                } else {
                    convertView = inflater.inflate(R.layout.weekend_detail_likeview, null);
                }
                return convertView;
            }
            return null;
        }

        public void setLayoutview(int i) {layoutview = i;}
    }
}
