package com.microweekend.mumu.microweekend.net;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Oauth2AccessToken {
    private String mAccessToken = "";
    private String mRefreshToken = "";
    private long mExpiresTime = 0L;

    public Oauth2AccessToken() {
    }

    public Oauth2AccessToken(String responsetext) {
        if(responsetext != null && responsetext.indexOf("{") >= 0) {
            try {
                JSONObject json = new JSONObject(responsetext);
                this.setToken(json.optString("access_token"));
                this.setExpiresIn(json.optString("expires_in"));
                this.setRefreshToken(json.optString("refresh_token"));
            } catch (JSONException var3) {
                ;
            }
        }

    }

    public Oauth2AccessToken(String accessToken, String expires_in) {
        this.mAccessToken = accessToken;
        this.mExpiresTime = System.currentTimeMillis() + Long.parseLong(expires_in) * 1000L;
    }

    public boolean isSessionValid() {
        return !TextUtils.isEmpty(this.mAccessToken) && (this.mExpiresTime == 0L || System.currentTimeMillis() < this.mExpiresTime);
    }

    public String getToken() {
        return this.mAccessToken;
    }

    public String getRefreshToken() {
        return this.mRefreshToken;
    }

    public void setRefreshToken(String mRefreshToken) {
        this.mRefreshToken = mRefreshToken;
    }

    public long getExpiresTime() {
        return this.mExpiresTime;
    }

    public void setExpiresIn(String expiresIn) {
        if(expiresIn != null && !expiresIn.equals("0")) {
            this.setExpiresTime(System.currentTimeMillis() + Long.parseLong(expiresIn) * 1000L);
        }

    }

    public void setExpiresTime(long mExpiresTime) {
        this.mExpiresTime = mExpiresTime;
    }

    public void setToken(String mToken) {
        this.mAccessToken = mToken;
    }
}
