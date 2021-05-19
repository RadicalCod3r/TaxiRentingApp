package com.example.taxirentingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Animation topAnimation;
    private Animation bottomAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        TextView textView1 = (TextView)findViewById(R.id.splash_text1);
        TextView textView2 = (TextView)findViewById(R.id.splash_text2);
        TextView textView3 = (TextView)findViewById(R.id.splash_text3);
        final LinearLayout logoLinear = (LinearLayout)findViewById(R.id.logo_text_linear);
        final ImageView imageView = (ImageView)findViewById(R.id.splash_image);

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        topAnimation.setDuration(2500);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        bottomAnimation.setDuration(2500);

        imageView.setAnimation(topAnimation);
        textView1.setAnimation(bottomAnimation);
        textView2.setAnimation(bottomAnimation);
        textView3.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(imageView,"logo_image");
                pairs[1] = new Pair<View,String>(logoLinear,"logo_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);

                startActivity(intent,options.toBundle());
                finish();
            }
        },5000);

    }
}