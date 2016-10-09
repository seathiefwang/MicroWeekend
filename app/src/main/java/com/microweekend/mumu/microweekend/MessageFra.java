package com.microweekend.mumu.microweekend;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.microweekend.mumu.microweekend.event.ResultEvent;

public class MessageFra extends WeekendFra {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.message_layout,container,false);
		return view;
	}

	@Override
	public void onEvent(ResultEvent e) {

	}
}
