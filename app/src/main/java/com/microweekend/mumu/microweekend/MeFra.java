package com.microweekend.mumu.microweekend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MeFra extends WeekendFra {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.friend_layout, container, false);

		return view;
	}
	/*public void showinfo(User user) {
		MyApplication.finalbitmap.display(usericon, user.getProfile_image_url());
		tvname.setText(user.getScreen_name());
		tvstucount.setText(user.getStatuses_count() + "       微博");
		tvfavcount.setText(user.getFavourites_count() + "          收藏");
		tvflowercount.setText(user.getFollowers_count() + "        粉丝");
	}

	@Override
	protected void handmessage(Message msg) {
		if (msg.what == LOADDATA_OVER) {
			adapter.notifyDataSetChanged();
		}
		super.handmessage(msg);
	}

	public void loadflowerlist() {
		FriendshipsAPI api = new FriendshipsAPI(accessToken);
		api.followers(WeiBoData.user.getId(), 20, 0, true, new RequestListener() {
			@Override
			public void onIOException(IOException arg0) {
			}

			@Override
			public void onError(WeiboException arg0) {
			}

			@Override
			public void onComplete4binary(ByteArrayOutputStream arg0) {
			}

			@Override
			public void onComplete(String arg0) {
				flowerlist.addAll(JsonParse.parseflowerlist(arg0));
				handler.sendEmptyMessage(LOADDATA_OVER);
			}
		});
	}
*/

}
