package com.microweekend.mumu.microweekend.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.microweekend.mumu.microweekend.MainTab;
import com.microweekend.mumu.microweekend.MkDetail;
import com.microweekend.mumu.microweekend.WeekendAct;
import com.microweekend.mumu.microweekend.adapter.AnsyLoad;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.microweekend.mumu.microweekend.R;
import com.microweekend.mumu.microweekend.customui.CircleImageView;
import com.microweekend.mumu.microweekend.entry.Statuses;
import com.microweekend.mumu.microweekend.util.MkConstants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mumu on 2016/9/30.
 */
public class TimeLineAdapter extends PtrrvBaseAdapter<TimeLineAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<Statuses> statuses;
    private String TAG = "TimeLineAdapter";
    private Context context;

    public TimeLineAdapter(Context context, ArrayList<Statuses> list) {
        super(context);
        statuses = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //只创建一次，且可能会被重复使用
        View view = mInflater.inflate(R.layout.weekend_content_itemview, null);
        ViewHolder holder = new ViewHolder(view);
        Log.d(TAG, "onCreateViewHoler");

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //每次滑到的选项都会立即绑定
        holder.getTvTitle().setText(statuses.get(position).getContent_title());
        Log.d(TAG, "onBindViewHoler" + position);

        String string = statuses.get(position).getPic_path();
        Log.d(TAG, string);
        if (!TextUtils.isEmpty(string))MkConstants.ansyLoad.setBitmapOfImageView(string, holder.getIvImg());

        string = statuses.get(position).getDisplayPic();
        if (!TextUtils.isEmpty(string))MkConstants.ansyLoad.setBitmapOfImageView(string, holder.getCircleImageView());

        holder.getTvUsername().setText(statuses.get(position).getUserNick());

        holder.getIvImg().setTag(position);
        holder.getIvImg().setOnClickListener(this);
        holder.getTvTitle().setTag(position);
        holder.getTvTitle().setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvstatusesimg:
            case R.id.tvstatusecontent:
                int position = (int)v.getTag();
                Intent intent = new Intent(context, MkDetail.class);
                intent.putExtra("content_id", statuses.get(position).getContent_id());
                context.startActivity(intent);
                break;
        }

    }

    @Override
    public int getItemCount() {
//        super.getItemCount();
        return statuses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private View view;
        private TextView tv_username;
        private TextView tv_title;
        private ImageView iv_img;
        private CircleImageView circleImageView;

        public String pic_url;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv_title = (TextView)itemView.findViewById(R.id.tvstatusecontent);
            iv_img = (ImageView)itemView.findViewById(R.id.tvstatusesimg);
            circleImageView = (CircleImageView)itemView.findViewById(R.id.circleImageView);
            tv_username = (TextView)itemView.findViewById(R.id.user_name);
        }

        public View getView() {
            return view;
        }

        public TextView getTvTitle() {
            return tv_title;
        }

        public ImageView getIvImg() {
            return iv_img;
        }

        public CircleImageView getCircleImageView() {
            return circleImageView;
        }

        public TextView getTvUsername() {
            return tv_username;
        }
    }
}
