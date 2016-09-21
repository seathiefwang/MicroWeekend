package com.microweekend.mumu.microweekend.net;

public interface RequestListener {
    void onComplete(String var1);

    void onComplete4binary();

    void onIOException();

    void onError();
}