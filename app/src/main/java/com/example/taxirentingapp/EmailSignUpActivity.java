package com.example.taxirentingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSignUpActivity extends AppCompatActivity {
    private Button signUpEmailBtn;
    private TextInputEditText emailEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sign_up);
        signUpEmailBtn = (Button)findViewById(R.id.sign_up_with_email);
        emailEdit = (TextInputEditText)findViewById(R.id.email_input);
        signUpEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final OTPSender sender = new OTPSender();
                sender.sendOtpToEmail(EmailSignUpActivity.this, emailEdit.getText().toString());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent emailSignupIntent = getIntent();
                        Intent intent = new Intent(EmailSignUpActivity.this,OtpActivity.class);
                        intent.putExtra("OTP",sender.getOtp());
                        intent.putExtra("username",emailSignupIntent.getStringExtra("username"));
                        intent.putExtra("password",emailSignupIntent.getStringExtra("password"));
                        intent.putExtra("email",emailEdit.getText().toString());
                        intent.putExtra("isDriver",emailSignupIntent.getBooleanExtra("isDriver",false));
                        startActivity(intent);
                        finish();
                    }
                },5000);

            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

}