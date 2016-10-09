package com.microweekend.mumu.microweekend.util;

import android.util.Log;

import com.microweekend.mumu.microweekend.entry.Statuses;
import com.microweekend.mumu.microweekend.entry.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mumu on 2016/10/1.
 */
public class ParseJson {

    /**
     *
     * 解析活动列表
     */
    public static ArrayList<Statuses> ParseStatuses(String json) {
        ArrayList<Statuses> statuses = new ArrayList<Statuses>();
        if (json == null) return statuses;
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return statuses;
        }
        JSONArray array;
        try {
            array = obj.getJSONArray("msg");
            if (array == null)return statuses;
            int n = array.length();
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                statuses.add(pareseMk(jsonObject));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return statuses;
        }
        return statuses;
    }

    /**
     * 解析单个活动内容
     * @param stuobj
     * @return
     */
    public static Statuses pareseMk(JSONObject stuobj) {
        Statuses sta = new Statuses();
        try {
            sta.setContent_id(stuobj.getInt("content_id"));
            sta.setContent_title(stuobj.getString("content_title"));
            sta.setContent_time(stuobj.getString("content_time"));
            sta.setContent_address(stuobj.getString("content_address"));
            sta.setLatitude(stuobj.getDouble("latitude"));
            sta.setLongitude(stuobj.getDouble("longitude"));
            sta.setCharge(stuobj.getString("charge"));
            sta.setCharge_type(stuobj.getString("charge_type"));
            sta.setContent_body(stuobj.getString("content_body"));
            sta.setPic_path(stuobj.getString("pic_path"));

            sta.setUserId(stuobj.getInt("user_id"));
            sta.setUserNick(stuobj.getString("nickname"));
            sta.setDisplayPic(stuobj.optString("display_pic"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sta;
    }

    public static User parseuser(String us) {
        Log.d("ParseJson", "parse user:"+us);
        User user = new User();
        if (us == null) return user;
        JSONObject obj = null;
        try {
            obj = new JSONObject(us);
            JSONObject js = obj.getJSONObject("msg");
            return parseuser(js);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("ParseJson", "parse user is error - msg");
            return user;
        }
    }

    /**
     * 解析单个User
     * @param js
     * @return
     */
    public static User parseuser(JSONObject js) {
        User user = new User();
        if (js == null) return user;
        try {
            user.setUserId(js.getInt("user_id"));
            user.setUserName(js.getString("user_name"));
            user.setNickname(js.getString("nickname"));
            user.setRealname(js.getString("realname"));
            user.setUserGender(js.getString("user_gender"));
            user.setDisplayPic(js.optString("display_pic"));
            Log.d("ParseJson", "parse user is ok");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("ParseJson", "parse user is error");
        }
        return user;
    }
}
