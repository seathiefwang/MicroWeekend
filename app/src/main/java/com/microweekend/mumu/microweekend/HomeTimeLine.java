package com.microweekend.mumu.microweekend;
/*
下拉刷新项目地址https://github.com/HomHomLin/Android-PullToRefreshRecyclerView
*/
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.microweekend.mumu.microweekend.adapter.PtrrvBaseAdapter;
import com.microweekend.mumu.microweekend.customui.DemoLoadMoreView;
import com.microweekend.mumu.microweekend.customui.DividerItemDecoration;

public class HomeTimeLine extends WeekendFra implements View.OnClickListener {

	private PullToRefreshRecyclerView lv_refresh;
	private TextView tv_head;
	private View view;
	private Context context;
	private String TAG = "HomeTimeLine";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		context = getActivity().getApplicationContext();
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
		loadMoreView.setLoadmoreString("loadmore");
		loadMoreView.setLoadMorePadding(100);

		lv_refresh.setLoadMoreFooter(loadMoreView);
//remove header
		lv_refresh.removeHeader();
// set true to open swipe(pull to refresh, default is true)
		lv_refresh.setSwipeEnable(true);
// set the layoutManager which to use
		lv_refresh.setLayoutManager(new LinearLayoutManager(context));
// set PagingableListener
		lv_refresh.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
			@Override
			public void onLoadMoreItems() {
				//do loadmore here
			}
		});
// set OnRefreshListener
		lv_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				// do refresh here
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

		PtrrvAdapter mAdapter = new PtrrvAdapter(context);
		mAdapter.setCount(10);
		lv_refresh.setAdapter(mAdapter);
		lv_refresh.onFinishLoading(true, false);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.tv_head:
				lv_refresh.smoothScrollToPosition(0);
				break;
		}
	}

	private class PtrrvAdapter extends PtrrvBaseAdapter<PtrrvAdapter.ViewHolder> implements View.OnClickListener {

		private View view;
		private ImageView iv_status;

		public PtrrvAdapter(Context context) {
			super(context);
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			view = mInflater.inflate(R.layout.weekend_content_itemview, null);
			Log.i(TAG, "adapter createView");
			initView();
			return new ViewHolder(view);
		}
		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			Log.i(TAG, "adapter bindView"+position);
		}

		public void initView() {
			iv_status = (ImageView)view.findViewById(R.id.tvstatusesimg);

			iv_status.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			startActivity(new Intent(context,MkDetail.class));
		}

		class ViewHolder extends RecyclerView.ViewHolder{
			public ViewHolder(View itemView) {
				super(itemView);
			}
		}
	}
}
