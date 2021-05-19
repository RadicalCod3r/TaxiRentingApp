package com.example.taxirentingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainSqliteDatabaseOpenHelper extends SQLiteOpenHelper {
    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "main_db";

    private static final String ACCOUNTS_TABLE_NAME = "accounts_tbl";
    private static final String USERS_TABLE_NAME = "users_tbl";
    private static final String DRIVERS_TABLE_NAME = "drivers_tbl";

    private static final String COLE_ID = "cole_id";
    private static final String COLE_USERNAME = "cole_username";
    private static final String COLE_PASSWORD = "cole_password";
    private static final String COLE_EMAIL = "cole_email";
    private static final String COLE_FULLNAME = "cole_fullname";
    private static final String COLE_PHONENUMBER = "cole_phonenumber";
    private static final String COLE_IMAGE = "cole_image";

    private static final String COLE_BALANCE = "cole_balance";
    private static final String COLE_BOOKINGS = "cole_bookings";
    private static final String COLE_
    private static final String CREATE_ACCOUNTS_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS "+ ACCOUNTS_TABLE_NAME + "("
            +COLE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLE_USERNAME+" TEXT, "
            +COLE_PASSWORD+" TEXT, "
            +COLE_EMAIL+" TEXT, "
            +COLE_FULLNAME+" TEXT, "
            +COLE_PHONENUMBER+" TEXT, "
            +COLE_IMAGE+" BLOB);";
    private static final String CREATE_USERS_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS "+ USERS_TABLE_NAME + "("
            +COLE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLE_USERNAME+" TEXT, "
            +COLE_PASSWORD+" TEXT, "
            +COLE_EMAIL+" TEXT, "
            +COLE_FULLNAME+" TEXT, "
            +COLE_PHONENUMBER+" TEXT, "
            +COLE_IMAGE+" BLOB, "
            +
    public MainSqliteDatabaseOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNTS_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addUser(User user){
        ContentValues cv = new ContentValues();
        cv.put(COLE_USERNAME, user.getUsername());
        cv.put(COLE_PASSWORD, user.getPassword());
        cv.put(COLE_EMAIL, user.getEmail());
        cv.put(COLE_FULLNAME, user.getFullName());
        cv.put(COLE_PHONENUMBER, user.getPhoneNumber());
        cv.put(COLE_IMAGE, user.getImage());
    }
}
