package com.microweekend.mumu.microweekend.event;

import com.microweekend.mumu.microweekend.db.MkHelper;

/**
 * Created by mumu on 2016/9/29.
 */
public class MkEvent {
    public int type = -1;
    private static MkHelper db;
    private String user_name;
    private String user_passwd;


    public String getUser_name() { return user_name; }
    public void setUser_name(String user_name) { this.user_name = user_name;}

    public String getUser_passwd() {return user_passwd;}

    public void setUser_passwd(String user_passwd) {this.user_passwd = user_passwd;}

    public MkHelper getDb() {
        return db;
    }

    public static void setDb(MkHelper d) {
        db = d;
    }
}
