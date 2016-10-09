package com.microweekend.mumu.microweekend.api;

import com.microweekend.mumu.microweekend.event.MkEvent;
import com.microweekend.mumu.microweekend.util.MkConstants;

/**
 * Created by mumu on 2016/9/20.
 */
public class UsersAPI extends MkAPI {

    private static final String SERVER_URL_PRIX = API_SERVER + "";

    public UsersAPI(MkEvent e) {
        super(e);
    }

    public void login(MkParameters param) {
        param.add("mod","User");
        param.add("act","login");
        request(SERVER_URL_PRIX, param, HTTPMETHOD_POST);
    }

    public void register(MkParameters param) {
        param.add("mod","User");
        param.add("act","register");
        request(SERVER_URL_PRIX,param,HTTPMETHOD_POST);
    }

    public void getUserInfo(MkParameters param) {
        param.add("mod","User");
        param.add("act","getUserInfo");
        param.add("user_name", MkConstants.USER_NAME);
        request(SERVER_URL_PRIX, param, HTTPMETHOD_POST);
    }

    public void setNickName(MkParameters param) {
        param.add("mod","User");
        param.add("act","setNickName");
        param.add("user_name", MkConstants.USER_NAME);
        request(SERVER_URL_PRIX, param, HTTPMETHOD_POST);
    }

    public void setGender(MkParameters param) {
        param.add("mod","User");
        param.add("act","setNickName");
        param.add("user_name", MkConstants.USER_NAME);
        request(SERVER_URL_PRIX, param, HTTPMETHOD_POST);
    }

    public void seDisplayPic(MkParameters param) {
        param.add("mod","User");
        param.add("act","setDisplayPic");
        param.add("user_name", MkConstants.USER_NAME);
        request(SERVER_URL_PRIX, param, HTTPMETHOD_POST);
    }
}
