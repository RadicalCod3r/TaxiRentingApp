package com.example.taxirentingapp;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class LogoTextView extends androidx.appcompat.widget.AppCompatTextView {
    public LogoTextView(Context context) {
        super(context);
        if (!isInEditMode()){
            setFont();
        }
    }

    public LogoTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()){
            setFont();
        }
    }

    public LogoTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()){
            setFont();
        }
    }

    private void setFont(){
        MyApplication myApplication = (MyApplication) getContext().getApplicationContext();
        setTypeface(myApplication.getLogoTypeface());
    }
}
