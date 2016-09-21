package com.microweekend.mumu.microweekend.api;

/**
 * Created by mumu on 2016/9/20.
 */
public class UsersAPI extends MkAPI {

    private static final String SERVER_URL_PRIX = API_SERVER + "";

    public UsersAPI() {
        super();
    }

    public void login() {
        MkParameters param = new MkParameters();
        param.add("User","test");
        request(SERVER_URL_PRIX,param,HTTPMETHOD_POST);
    }
}
