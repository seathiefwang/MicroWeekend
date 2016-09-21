package com.microweekend.mumu.microweekend.entry;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Emotion implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    String name;
    Bitmap bitmap;
    public Emotion(){}
    public Emotion(String name, Bitmap bitmap) {
        super();
        this.name = name;
        this.bitmap = bitmap;
    }
    
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    
    
}
