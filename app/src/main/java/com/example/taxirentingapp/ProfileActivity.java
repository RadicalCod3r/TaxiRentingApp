package com.example.taxirentingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;


public class ProfileActivity extends AppCompatActivity {
    private final int ID_ACCOUNT = 1;
    private final int ID_ORDER = 2;
    private final int ID_HISTORY = 3;
    private MeowBottomNavigation navigation;
    private Button editBtn;
    private ImageView profileImage;
    private TextView fullNameTxt;
    private TextView usernameTxt;
    private TextView balanceTxt;
    private TextView balanceNumber;
    private TextView bookingsTxt;
    private TextView bookingsNumber;
    private TextView completeProfTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button logoutBtn = (Button)findViewById(R.id.logout_button);
        initViews();
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        String email = intent.getStringExtra("email");
        boolean isDriver = intent.getBooleanExtra("isDriver",false);

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setDriver(isDriver);
        account.setEmail(email);

        usernameTxt.setText(account.getUsername());
        if(account.getFullName() == null){
            fullNameTxt.setText("GUEST");
        }
        if(account.getImage() == null){
            profileImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.rental_profile,null));
        }
        if(account.isDriver()){
            Driver driver = new Driver();
            driver.setUsername(account.getUsername());
            driver.setPassword(account.getPassword());
            driver.setEmail(account.getEmail());
            balanceTxt.setText("Income");
            bookingsTxt.setText("Customers");
            if(driver.getUserList() != null){
                bookingsNumber.setText(String.valueOf(driver.getUserList().size()));
            }else{
                bookingsNumber.setText("0");
            }
            balanceNumber.setText("$" + driver.getIncome());
        }else{
            User user = new User();
            user.setUsername(account.getUsername());
            user.setPassword(account.getPassword());
            user.setEmail(account.getEmail());
            balanceTxt.setText("Balance");
            bookingsTxt.setText("Bookings");
            if(user.getDriversList() != null){
                bookingsNumber.setText(user.getDriversList().size());
            }else{
                bookingsNumber.setText("0");
            }
            balanceNumber.setText("$"+user.getBalance());
        }
        navigation = (MeowBottomNavigation)findViewById(R.id.bottom_navigation);
        navigation.add(new MeowBottomNavigation.Model(ID_ACCOUNT,R.drawable.ic_baseline_account_circle_24));
        navigation.add(new MeowBottomNavigation.Model(ID_ORDER,R.drawable.ic_baseline_directions_car_24));
        navigation.add(new MeowBottomNavigation.Model(ID_HISTORY,R.drawable.ic_baseline_history_24));
        navigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case ID_ORDER:
                        Intent orderIntent = new Intent(ProfileActivity.this,UserMapActivity.class);
                        startActivity(orderIntent);
                        overridePendingTransition(0,0);
                        break;
                }
            }
        });
        navigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                String name = "";
                switch (item.getId()){
                    case ID_ACCOUNT: name = "Account";
                    break;

                    case ID_ORDER: name = "Order";
                    break;

                    case ID_HISTORY: name = "History";
                    break;
                }
            }
        });
        navigation.setCount(ID_ACCOUNT,"2");
        navigation.show(ID_ACCOUNT,true);

        editBtn = (Button)findViewById(R.id.edit_profile_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    private void initViews(){
        profileImage = (ImageView) findViewById(R.id.profile_image);
        fullNameTxt = (TextView)findViewById(R.id.full_name);
        usernameTxt = (TextView) findViewById(R.id.username_profile);
        balanceTxt = (TextView)findViewById(R.id.account_balance_txt);
        bookingsTxt = (TextView)findViewById(R.id.account_bookings_txt);
        balanceNumber = (TextView)findViewById(R.id.account_balance);
        bookingsNumber = (TextView)findViewById(R.id.account_bookings);
        completeProfTxt = (TextView)findViewById(R.id.complete_profile_text);
    }
}