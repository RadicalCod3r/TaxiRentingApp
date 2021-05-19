package com.example.taxirentingapp;

import android.app.Application;
import android.graphics.Typeface;

public class MyApplication extends Application {
    private Typeface logoTypeface;
    private Typeface headerTypeface;
    @Override
    public void onCreate() {
        super.onCreate();
        logoTypeface = Typeface.createFromAsset(getAssets(),"fonts/quick_kiss.ttf");
        headerTypeface = Typeface.createFromAsset(getAssets(),"fonts/bungee.ttf");
    }
    public Typeface getLogoTypeface(){
        return this.logoTypeface;
    }
    public Typeface getHeaderTypeface(){
        return this.headerTypeface;
    }
}
