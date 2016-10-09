package com.microweekend.mumu.microweekend;
/*
下拉刷新项目地址https://github.com/HomHomLin/Android-PullToRefreshRecyclerView
*/
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.microweekend.mumu.microweekend.adapter.TimeLineAdapter;
import com.microweekend.mumu.microweekend.customui.DemoLoadMoreView;
import com.microweekend.mumu.microweekend.customui.DividerItemDecoration;
import com.microweekend.mumu.microweekend.db.MkHelper;
import com.microweekend.mumu.microweekend.entry.Statuses;
import com.microweekend.mumu.microweekend.event.ResultEvent;
import com.microweekend.mumu.microweekend.event.StatusEvent;
import com.microweekend.mumu.microweekend.util.ParseJson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class HomeTimeLine extends WeekendFra implements View.OnClickListener {

	private ArrayList<Statuses> statuses;
	private TimeLineAdapter mAdapter;
	private PullToRefreshRecyclerView lv_refresh;
	private TextView tv_head;
	private View view;
	private String TAG = "HomeTimeLine";
	private int page = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.hometimeine_lay, container, false);
		initView();
		return view;
	}

	public void initView() {
		tv_head = (TextView)view.findViewById(R.id.tv_head);

		tv_head.setOnClickListener(this);
		initPullView();
	}

	public void initPullView() {
		lv_refresh = (PullToRefreshRecyclerView)(view.findViewById(R.id.listView_homeline));

		// custom own load-more-view and add it into ptrrv
		DemoLoadMoreView loadMoreView = new DemoLoadMoreView(context, lv_refresh.getRecyclerView());
		loadMoreView.setLoadmoreString("正在加载");
		loadMoreView.setLoadMorePadding(100);

		lv_refresh.setLoadMoreFooter(loadMoreView);
//remove header
//		lv_refresh.removeHeader();
// set true to open swipe(pull to refresh, default is true)
		lv_refresh.setSwipeEnable(true);
// set the layoutManager which to use
		lv_refresh.setLayoutManager(new LinearLayoutManager(context));
// set PagingableListener
		lv_refresh.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
			@Override
			public void onLoadMoreItems() {
				//do loadmore here
				page++;
				StatusEvent e = new StatusEvent();
				e.type = StatusEvent.TYPE_TIMELINE;
				e.page = page;
				e.count = 10;
				EventBus.getDefault().post(e);
			}
		});
// set OnRefreshListener
		lv_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				// do refresh here
				page = 0;
				StatusEvent e = new StatusEvent();
				e.type = StatusEvent.TYPE_TIMELINE;
				e.page = page;
				e.count = 10;
				EventBus.getDefault().post(e);
			}
		});
// add item divider to recyclerView
		lv_refresh.getRecyclerView().addItemDecoration(new DividerItemDecoration(context,
				DividerItemDecoration.VERTICAL_LIST));
// add headerView
//		lv_refresh.addHeaderView(View.inflate(context, R.layout.header, null));
//set EmptyVIEW
		lv_refresh.setEmptyView(View.inflate(context, R.layout.empty_view, null));

// set loadmore String
		lv_refresh.setLoadmoreString("loading");

// set loadmore enable, onFinishLoading(can load more? , select before item)

		statuses = ParseJson.ParseStatuses(new MkHelper(context).getContent());

		mAdapter = new TimeLineAdapter(context, statuses);

		mAdapter.setCount(statuses.size());
		lv_refresh.setAdapter(mAdapter);
		lv_refresh.onFinishLoading(true, false);

		//没有本地数据就从服务器获取数据
		if (statuses.size() == 0) {
			StatusEvent e = new StatusEvent();
			e.type = StatusEvent.TYPE_TIMELINE;
			e.page = page;
			EventBus.getDefault().post(e);
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.tv_head:
				lv_refresh.smoothScrollToPosition(0);
				break;
		}
	}

	public void onEvent(ResultEvent e) {
		Log.d(TAG, e.toString());
		switch (e.type) {
			case StatusEvent.TYPE_TIMELINE:
				refreshHomeLine(e);
				if (lv_refresh.isRefreshing()) {
					lv_refresh.setOnRefreshComplete();
					lv_refresh.onFinishLoading(true, false);
				} else {
					lv_refresh.setOnLoadMoreComplete();
					if (page < 3)
						lv_refresh.onFinishLoading(true, false);
					else
						lv_refresh.onFinishLoading(false, false);
				}
				break;
		}
	}

	public void refreshHomeLine(ResultEvent e) {
		if (e.getCode() == 1) {
			Log.d(TAG, "msg" + e.getCode());
			if (page == 0) statuses.clear();
			statuses.addAll(ParseJson.ParseStatuses(e.getJson()));
			mAdapter.setCount(statuses.size());
			mAdapter.notifyDataSetChanged();
		}
	}
}
