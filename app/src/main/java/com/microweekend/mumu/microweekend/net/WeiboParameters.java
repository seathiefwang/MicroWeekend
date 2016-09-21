package com.microweekend.mumu.microweekend.net;

import android.text.TextUtils;
import java.util.ArrayList;

public class WeiboParameters {
    private ArrayList<String> mKeys = new ArrayList();
    private ArrayList<String> mValues = new ArrayList();

    public WeiboParameters() {
    }

    public void add(String key, String value) {
        if(!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            this.mKeys.add(key);
            this.mValues.add(value);
        }

    }

    public void add(String key, int value) {
        this.mKeys.add(key);
        this.mValues.add(String.valueOf(value));
    }

    public void add(String key, long value) {
        this.mKeys.add(key);
        this.mValues.add(String.valueOf(value));
    }

    public void remove(String key) {
        int firstIndex = this.mKeys.indexOf(key);
        if(firstIndex >= 0) {
            this.mKeys.remove(firstIndex);
            this.mValues.remove(firstIndex);
        }

    }

    public void remove(int i) {
        if(i < this.mKeys.size()) {
            this.mKeys.remove(i);
            this.mValues.remove(i);
        }

    }

    private int getLocation(String key) {
        return this.mKeys.contains(key)?this.mKeys.indexOf(key):-1;
    }

    public String getKey(int location) {
        return location >= 0 && location < this.mKeys.size()?(String)this.mKeys.get(location):"";
    }

    public String getValue(String key) {
        int index = this.getLocation(key);
        return index >= 0 && index < this.mKeys.size()?(String)this.mValues.get(index):null;
    }

    public String getValue(int location) {
        if(location >= 0 && location < this.mKeys.size()) {
            String rlt = (String)this.mValues.get(location);
            return rlt;
        } else {
            return null;
        }
    }

    public int size() {
        return this.mKeys.size();
    }

    public void addAll(WeiboParameters parameters) {
        for(int i = 0; i < parameters.size(); ++i) {
            this.add(parameters.getKey(i), parameters.getValue(i));
        }

    }

    public void clear() {
        this.mKeys.clear();
        this.mValues.clear();
    }
}
