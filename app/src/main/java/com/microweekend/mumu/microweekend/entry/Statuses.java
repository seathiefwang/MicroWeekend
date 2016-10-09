package com.microweekend.mumu.microweekend.entry;

public class Statuses {

//	`content_id` int(10) NOT NULL AUTO_INCREMENT,
//	`user_id` int(10) NOT NULL,
//	`content_title` varchar(50) NOT NULL,
//	`content_body` text NOT NULL,
//	`content_time` varchar(50) NOT NULL,
//	`content_address` varchar(50) NOT NULL,
//	`content_pay` varchar(50) NOT NULL,
//	`index_pic` int(10) NOT NULL,
	
	public Statuses getRetweeted_status() {
		return retweeted_status;
	}
	public void setRetweeted_status(Statuses retweeted_status) {
		this.retweeted_status = retweeted_status;
	}

	private long content_id;
	private String content_title;//标题
	private String content_body;//活动详情
	private String content_time;//活动时间
	private String content_address;//活动地址
	private double latitude;//经度
	private double longitude;//纬度
	private String charge_type;//
	private String charge;
	private String pic_path	;//	图片地址
	private String source;//活动的网页地址，待实现
	private Statuses retweeted_status;//转发活动

	private long userId;
	private String displayPic;
	private String userNick;

	public long getContent_id() {
		return content_id;
	}

	public String getContent_address() {
		return content_address;
	}

	public String getContent_body() {
		return content_body;
	}

	public String getContent_time() {
		return content_time;
	}

	public String getContent_title() {
		return content_title;
	}

	public String getPic_path() {
		return pic_path;
	}

	public String getSource() {
		return source;
	}

	public void setContent_id(long content_id) {
		this.content_id = content_id;
	}

	public void setContent_address(String content_address) {
		this.content_address = content_address;
	}

	public void setContent_body(String content_body) {
		this.content_body = content_body;
	}

	public void setContent_time(String content_time) {
		this.content_time = content_time;
	}

	public void setContent_title(String content_title) {
		this.content_title = content_title;
	}

	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getDisplayPic() {
		return displayPic;
	}

	public void setDisplayPic(String displayPic) {
		this.displayPic = displayPic;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getCharge_type() {
		return charge_type;
	}

	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
