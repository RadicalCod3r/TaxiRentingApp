package com.example.taxirentingapp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class HeaderTextView extends androidx.appcompat.widget.AppCompatTextView {
    public HeaderTextView(Context context) {
        super(context);
        if(!isInEditMode()){
            setFont();
        }
    }

    public HeaderTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()){
            setFont();
        }
    }

    public HeaderTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!isInEditMode()){
            setFont();
        }
    }

    private void setFont(){
        MyApplication myApplication = (MyApplication) getContext().getApplicationContext();
        setTypeface(myApplication.getHeaderTypeface());
    }
}
