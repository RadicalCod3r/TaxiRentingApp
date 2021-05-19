package com.example.taxirentingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private ImageView logoImageView;
    private LinearLayout greetingLinear;
    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private Button forgotPassBtn;
    private Button signInBtn;
    private Button signupBtn;
    private LinearLayout userLogin;
    private LinearLayout driverLogin;
    private ImageView userIcon, driverIcon;
    private TextView userText, driverText;
    boolean isDriver = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        Button button = (Button)findViewById(R.id.signup_button_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                intent.putExtra("isDriver", isDriver);
                /*Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(logoImageView,"logo_image");
                pairs[1] = new Pair<View,String>(greetingLinear,"logo_text");
                pairs[2] = new Pair<View,String>(usernameInput,"username_trans");
                pairs[3] = new Pair<View,String>(passwordInput,"password_trans");
                pairs[4] = new Pair<View,String>(forgotPassBtn,"password_trans_2");
                pairs[5] = new Pair<View,String>(signInBtn,"submit_button_trans");
                pairs[6] = new Pair<View,String>(signupBtn,"signup_trans");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);*/
                startActivity(intent);
            }
        });
        driverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDriver = true;
                driverIcon.setImageDrawable(ResourcesCompat.getDrawable(LoginActivity.this.getResources(),R.drawable.ic_baseline_drive_eta_white,null));
                driverText.setTextColor(Color.WHITE);
                userIcon.setImageDrawable(ResourcesCompat.getDrawable(LoginActivity.this.getResources(),R.drawable.ic_baseline_account_circle_black,null));
                userText.setTextColor(Color.BLACK);
                driverLogin.setBackgroundColor(Color.BLACK);
                userLogin.setBackgroundColor(Color.WHITE);
                signupBtn.setText("NEW DRIVER? SIGN UP");
            }
        });
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDriver = false;
                driverIcon.setImageDrawable(ResourcesCompat.getDrawable(LoginActivity.this.getResources(),R.drawable.ic_baseline_drive_eta_black,null));
                driverText.setTextColor(Color.BLACK);
                userIcon.setImageDrawable(ResourcesCompat.getDrawable(LoginActivity.this.getResources(),R.drawable.ic_baseline_account_circle_24,null));
                userText.setTextColor(Color.WHITE);
                userLogin.setBackgroundColor(Color.BLACK);
                driverLogin.setBackgroundColor(Color.WHITE);
                signupBtn.setText("NEW USER? SIGN UP");
            }
        });
    }
    private void initViews(){
        logoImageView = (ImageView)findViewById(R.id.logo_image_login);
        greetingLinear = (LinearLayout)findViewById(R.id.greeting_linear);
        usernameInput = (TextInputEditText)findViewById(R.id.username_input_login);
        passwordInput = (TextInputEditText)findViewById(R.id.password_input_login);
        forgotPassBtn = (Button)findViewById(R.id.forgot_password_btn);
        signInBtn = (Button)findViewById(R.id.sign_in_btn);
        signupBtn = (Button)findViewById(R.id.signup_button_login);
        userLogin = (LinearLayout)findViewById(R.id.user_login);
        driverLogin = (LinearLayout)findViewById(R.id.driver_login);
        userIcon = (ImageView) findViewById(R.id.user_icon);
        driverIcon = (ImageView) findViewById(R.id.driver_icon);
        userText = (TextView)findViewById(R.id.user_text);
        driverText = (TextView)findViewById(R.id.driver_text);
    }
}