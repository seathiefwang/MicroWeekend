package com.microweekend.mumu.microweekend.api;

import com.microweekend.mumu.microweekend.event.MkEvent;
import com.microweekend.mumu.microweekend.util.MkConstants;

/**
 * Created by mumu on 2016/9/29.
 */
public class StatusAPI extends MkAPI{

    private static final String SERVER_URL_PRIX = API_SERVER + "";

    public StatusAPI(MkEvent e) {
        super(e);
    }

    public void sendMk(MkParameters param) {
        param.add("mod","Status");
        param.add("act","sendMk");
        param.add("user_name", MkConstants.USER_NAME);
        request(SERVER_URL_PRIX, param, HTTPMETHOD_POST);
    }

    public void timeLine(MkParameters param) {
        param.add("mod","Status");
        param.add("act","timeLine");
        param.add("user_name", MkConstants.USER_NAME);
        request(SERVER_URL_PRIX, param, HTTPMETHOD_POST);
    }

    public void uploadPic(MkParameters param) {
        param.add("mod","Status");
        param.add("act","upload");
        param.add("user_name", MkConstants.USER_NAME);
        request(SERVER_URL_PRIX, param, HTTPMETHOD_POST);
    }

    public void createOrder(MkParameters param) {
        param.add("mod","Status");
        param.add("act","createOrder");
        param.add("user_name", MkConstants.USER_NAME);
        request(SERVER_URL_PRIX, param, HTTPMETHOD_POST);
    }

    public void getSended(MkParameters param) {
        param.add("mod","Status");
        param.add("act","getSended");
        param.add("user_name", MkConstants.USER_NAME);
        request(SERVER_URL_PRIX, param, HTTPMETHOD_POST);
    }

    public void getJoined(MkParameters param) {
        param.add("mod","Status");
        param.add("act","getJoined");
        param.add("user_name", MkConstants.USER_NAME);
        request(SERVER_URL_PRIX, param, HTTPMETHOD_POST);
    }

    public String uploadPicForResult(MkParameters param) {
        param.add("mod","Status");
        param.add("act", "upload");
        param.add("user_name", MkConstants.USER_NAME);
        return requestForResult(SERVER_URL_PRIX, param, HTTPMETHOD_POST);
    }

}
