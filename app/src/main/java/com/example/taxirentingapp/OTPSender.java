package com.example.taxirentingapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class OTPSender {
    private String otp;
    public String getOtp() {
        return otp;
    }

    public void sendOtpToEmail(final Context context, String email){
        final String appEmail = "RadsRental@gmail.com";
        final String pass = "1.3.5rt.";
        Properties props = new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port","587");
        Session session = Session.getInstance(props,new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(appEmail,pass);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(appEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Email Authentication");
            otp = generateOTP();
            message.setText("Verification Code for Rad's Rental is : " + otp);
            Transport.send(message);
            Toast.makeText(context, "Verification Code is sent to you email", Toast.LENGTH_SHORT).show();

        } catch (MessagingException e) {
            e.printStackTrace();
            Log.i("otp sender", "sendOtpToEmail: "+e.toString());
            Toast.makeText(context, "Sending Verification Code has been failed", Toast.LENGTH_SHORT).show();
        }

    }
    private String generateOTP(){
        int len = 4;
        String numbers = "0123456789";
        Random random = new Random();
        char[] password = new char[len];
        for(int i=0;i<len;i++){
            password[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return String.valueOf(password);
    }
}
