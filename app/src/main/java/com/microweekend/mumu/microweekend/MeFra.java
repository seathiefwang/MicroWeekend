package com.microweekend.mumu.microweekend;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.microweekend.mumu.microweekend.adapter.JoinedAdapter;
import com.microweekend.mumu.microweekend.customui.CircleImageView;
import com.microweekend.mumu.microweekend.db.MkHelper;
import com.microweekend.mumu.microweekend.entry.Statuses;
import com.microweekend.mumu.microweekend.entry.User;
import com.microweekend.mumu.microweekend.event.ResultEvent;
import com.microweekend.mumu.microweekend.event.StatusEvent;
import com.microweekend.mumu.microweekend.util.MkConstants;
import com.microweekend.mumu.microweekend.util.ParseJson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class MeFra extends WeekendFra implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

	public final static int REQUEST_CODE = 240;
	private User userinfo;

	private View view;
	private TextView tv_nickname;
	private CircleImageView iv_displaypic;
	private ImageView iv_setup;

	private RadioButton rb_sended;
	private RadioButton rb_joined;
	private RadioButton rb_collect;
	private ListView listView;
	private ArrayList<Statuses> listinfo;
	private JoinedAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.me_layout, container, false);
		initView();
		return view;
	}

	public void initView() {
		tv_nickname = (TextView)view.findViewById(R.id.nickname);
		iv_displaypic = (CircleImageView)view.findViewById(R.id.circleImageView);
		iv_setup = (ImageView) view.findViewById(R.id.imageView);
		rb_sended = (RadioButton)view.findViewById(R.id.radioButton1);
		rb_joined = (RadioButton)view.findViewById(R.id.radioButton2);
		rb_collect = (RadioButton)view.findViewById(R.id.radioButton3);
		listView = (ListView)view.findViewById(R.id.listView);

		userinfo = ParseJson.parseuser(new MkHelper(context).getUser());

		listinfo = ParseJson.ParseStatuses(new MkHelper(context).getSended());
		adapter = new JoinedAdapter(context, listinfo);
		listView.setAdapter(adapter);

		setUserinfo();
		initDialog();
	}

	private void initDialog() {
		iv_setup.setOnClickListener(this);
		iv_displaypic.setOnClickListener(this);

		rb_sended.setOnCheckedChangeListener(this);
		rb_joined.setOnCheckedChangeListener(this);
		rb_collect.setOnCheckedChangeListener(this);
	}

	private void setUserinfo() {
		String string = userinfo.getNickname();
		if (string == null || string.equals("")) {
			tv_nickname.setText("我");
		} else {
			tv_nickname.setText(string);
		}
		string = userinfo.getDisplayPic();
		if (!TextUtils.isEmpty(string)) {
			MkConstants.ansyLoad.setBitmapOfImageView(string, iv_displaypic);
		}
	}
	@Override
	public void onEvent(ResultEvent e) {
		switch (e.type) {
			case StatusEvent.TYPE_GETSENDED:
				if (e.getCode() == 1) {
					listinfo.clear();
					listinfo.addAll(ParseJson.ParseStatuses(e.getJson()));
					adapter.notifyDataSetChanged();
				}
				break;
			case StatusEvent.TYPE_GETJOINED:
				if (e.getCode() == 1) {
					listinfo.clear();
					listinfo.addAll(ParseJson.ParseStatuses(e.getJson()));
					adapter.notifyDataSetChanged();
				}
				break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.imageView:
			case R.id.circleImageView:
				startActivity(new Intent(context, SetupAct.class));
				break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
				case R.id.radioButton1:
					StatusEvent e = new StatusEvent();
					e.type = StatusEvent.TYPE_GETSENDED;
					EventBus.getDefault().post(e);
					break;
				case R.id.radioButton2:
					e = new StatusEvent();
					e.type = StatusEvent.TYPE_GETJOINED;
					EventBus.getDefault().post(e);
					break;
				case R.id.radioButton3:

					break;
			}
		}
	}
}
