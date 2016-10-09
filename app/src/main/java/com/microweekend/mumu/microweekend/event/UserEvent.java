package com.microweekend.mumu.microweekend.event;

/**
 * Created by mumu on 2016/9/29.
 */
public class UserEvent extends MkEvent {
    public final static int TYPE_LOGIN = 101;
    public final static int TYPE_REGISTER = 102;
    public final static int TYPE_SETNICKNAME = 103;
    public final static int TYPE_SETGENDER = 104;
    public final static int TYPE_GETUSERINFO = 105;
    public final static int TYPE_SETDISPLAYPIC = 106;

    private String string;
    private String pic;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
