package com.microweekend.mumu.microweekend.entry;

public class User {
	//user_id
	//user_name
	//realname
	//nickname
	//user_gender
	//display_pic
	
	private long user_id;
	private String user_name;
	private String nickname;
	private String realname;
	private String userGender;
	private String displayPic;

	public long getUserId() {
		return user_id;
	}
	public String getNickname() {
		return nickname;
	}
	public String getRealname() {
		return realname;
	}
	public String getUserName() {
		return user_name;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public void setUserId(long user_id) {
		this.user_id = user_id;
	}
	public void setUserName(String user_name) {
		this.user_name = user_name;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getDisplayPic() {
		return displayPic;
	}

	public void setDisplayPic(String displayPic) {
		this.displayPic = displayPic;
	}
}
