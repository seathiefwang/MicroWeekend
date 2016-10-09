package com.microweekend.mumu.microweekend.event;

import org.json.JSONException;
import org.json.JSONObject;

/**返回的结果处理事件
 * Created by mumu on 2016/9/29.
 */
public class ResultEvent extends MkEvent {
    private int code;
    private String json;
    private Object msg;
    public ResultEvent() {

    }
    public int getCode(){return code;}

    public Object getMsg() {return msg;}

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
        try {
            JSONObject js = new JSONObject(json);
            code = js.getInt("code");
            msg = js.get("msg");
            switch (this.type) {
                case StatusEvent.TYPE_TIMELINE:
                    if (code == 1)this.getDb().insertContent(json);
                    break;
                case UserEvent.TYPE_GETUSERINFO:
                    if (code == 1)this.getDb().insertUser(json);
                    break;
                case StatusEvent.TYPE_GETJOINED:
                    if (code == 1)this.getDb().insertJoined(json);
                    break;
                case StatusEvent.TYPE_GETSENDED:
                    if (code == 1)this.getDb().insertSended(json);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            code = -1;
            msg = null;
        }
    }
}
