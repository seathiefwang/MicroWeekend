package com.microweekend.mumu.microweekend.event;

/**
 * Created by mumu on 2016/9/29.
 */
public class StatusEvent extends MkEvent {
    public final static int TYPE_SENDMK = 201;
    public final static int TYPE_TIMELINE = 202;
    public final static int TYPE_UPLOADPIC = 203;
    public final static int TYPE_CREATEORDER = 204;
    public final static int TYPE_GETSENDED = 205;
    public final static int TYPE_GETJOINED = 206;
    public final static int TYPE_GETCOLLECT = 207;
    private String index_pic;
    public String title;
    public String time;
    public String address;
    public String charge_type;
    public int charge = 0;
    public String body;
    public long content_id;
    public double latitude;
    public double longitude;
    public int page = 0;
    public int count = 10;
    public String getPic() {return index_pic;}

    public void setPic(String index_pic) {
        this.index_pic = index_pic;
    }

}
