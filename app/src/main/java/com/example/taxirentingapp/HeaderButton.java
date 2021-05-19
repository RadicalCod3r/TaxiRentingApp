package com.example.taxirentingapp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class HeaderButton extends androidx.appcompat.widget.AppCompatButton {
    public HeaderButton(Context context) {
        super(context);
        if (!isInEditMode()){
            setFont();
        }
    }

    public HeaderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()){
            setFont();
        }
    }

    public HeaderButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()){
            setFont();
        }
    }
    private void setFont(){
        MyApplication myApplication = (MyApplication)getContext().getApplicationContext();
        setTypeface(myApplication.getHeaderTypeface());
    }
}
