package com.example.taxirentingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class OtpActivity extends AppCompatActivity {
    private OtpEditText otpEditText;
    private Button resendOtpBtn;
    private Button checkOtpBtn;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        initViews();
        mAuth = FirebaseAuth.getInstance();
        checkOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otpIntent = getIntent();
                String otp = otpIntent.getStringExtra("OTP");
                if(otpEditText.getText().toString().equals(otp)){
                    Toast.makeText(OtpActivity.this, "You are successfully signed in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OtpActivity.this,ProfileActivity.class);
                    String username = otpIntent.getStringExtra("username");
                    String password = otpIntent.getStringExtra("password");
                    String email = otpIntent.getStringExtra("email");
                    boolean isDriver = otpIntent.getBooleanExtra("isDriver",false);

                    intent.putExtra("username",username);
                    intent.putExtra("password",password);
                    intent.putExtra("email",email);
                    intent.putExtra("isDriver",isDriver);

                    saveAccountInDatabase(username,password,email,isDriver);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(OtpActivity.this, "Enter the correct otp", Toast.LENGTH_SHORT).show();
                }
            }
        });
        resendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otpIntent = getIntent();
                OTPSender sender = new OTPSender();
                sender.sendOtpToEmail(OtpActivity.this,otpIntent.getStringExtra("email"));
                String otp1 = sender.getOtp();
                Intent intent = new Intent(OtpActivity.this,OtpActivity.class);
                intent.putExtra("OTP",otp1);
                startActivity(intent);
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        });
    }
    private void initViews(){
        otpEditText = (OtpEditText)findViewById(R.id.otp_edit);
        resendOtpBtn = (Button)findViewById(R.id.resend_otp_btn);
        checkOtpBtn = (Button)findViewById(R.id.check_otp_btn);
    }
    private void saveAccountInDatabase(String username, String password, String email, boolean isDriver){
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setEmail(email);
        account.setDriver(isDriver);

        MainSqliteDatabaseOpenHelper openHelper = new MainSqliteDatabaseOpenHelper(this);
        openHelper.addUser(account);
    }
}