package com.microweekend.mumu.microweekend.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.microweekend.mumu.microweekend.R;
import com.microweekend.mumu.microweekend.customui.CircleImageView;
import com.microweekend.mumu.microweekend.entry.Statuses;
import com.microweekend.mumu.microweekend.util.MkConstants;

import java.util.ArrayList;

/**
 * Created by mumu on 2016/10/8.
 */
public class JoinedAdapter extends BaseAdapter {
    private ArrayList<Statuses> statuses;
    private LayoutInflater mInflater;
    private ViewHolder holder;
    private String TAG = "JoinedAdapter";
    private Context context;

    public JoinedAdapter(Context c, ArrayList<Statuses> list) {
        mInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = c;
        statuses = list;
    }

    @Override
    public int getCount() {
        return statuses.size();
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
        if (convertView != null) {
            holder = (ViewHolder)convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.weekend_content_itemview, null);
            holder = new ViewHolder();
            holder.iv_user = (CircleImageView)convertView.findViewById(R.id.circleImageView);
            holder.tv_user = (TextView)convertView.findViewById(R.id.user_name);
            holder.iv_content = (ImageView)convertView.findViewById(R.id.tvstatusesimg);
            holder.tv_title = (TextView)convertView.findViewById(R.id.tvstatusecontent);
            convertView.setTag(holder);
        }
        Statuses sta = statuses.get(position);
        if (sta != null) {
            holder.tv_title.setText(sta.getContent_title());

            String string = sta.getPic_path();
            Log.d(TAG, string);
            if (!TextUtils.isEmpty(string)) MkConstants.ansyLoad.setBitmapOfImageView(string, holder.iv_content);

            string = sta.getDisplayPic();
            if (!TextUtils.isEmpty(string))MkConstants.ansyLoad.setBitmapOfImageView(string, holder.iv_user);

            holder.tv_user.setText(sta.getUserNick());
        }
        return convertView;
    }

    class ViewHolder {
        public CircleImageView iv_user;
        public TextView tv_user;
        public ImageView iv_content;
        public TextView tv_title;
    }
}
