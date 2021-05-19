package com.example.taxirentingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignupActivity extends AppCompatActivity {
    public static final int GOOGLE_SIGN_IN_CODE = 101;
    private ImageButton signupBtn;
    private TextInputEditText userEdit;
    private TextInputEditText passEdit;
    private TextInputEditText reEnterPassEdit;
    private RadioButton googleSignInRadio;
    private RadioButton emailSignInRadio;
    private GoogleSignInOptions mGoogleOptions;
    private GoogleSignInClient mGoogleClient;
    private FirebaseAuth mAuth;
    private static final int GOOGLE_SIGN_IN_REQUEST_CODE = 1000;
    private String mUsername;
    private String mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViews();
        mGoogleOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1022295659187-h821ov3224j0g24adu9qq7vf2rh02mvi.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleClient = GoogleSignIn.getClient(this,mGoogleOptions);
        mAuth = FirebaseAuth.getInstance();
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsername = userEdit.getText().toString();
                mPassword = passEdit.getText().toString();
                String reEnteredPass = reEnterPassEdit.getText().toString();

                if(!mUsername.isEmpty() && !mPassword.isEmpty() && !reEnteredPass.isEmpty() && mPassword.length()>=5 && mPassword.equals(reEnteredPass)){
                    if(googleSignInRadio.isChecked()){
                        Intent sign = mGoogleClient.getSignInIntent();
                        startActivityForResult(sign,GOOGLE_SIGN_IN_REQUEST_CODE);
                    }else if(emailSignInRadio.isChecked()){
                        Intent toEmailSignUp = new Intent(SignupActivity.this,EmailSignUpActivity.class);
                        toEmailSignUp.putExtra("username",mUsername);
                        toEmailSignUp.putExtra("password",mPassword);
                        toEmailSignUp.putExtra("isDriver",getIntent().getBooleanExtra("isDriver",false));
                        startActivity(toEmailSignUp);
                    }
                }else{
                    if(mUsername.isEmpty())
                        userEdit.setError("username is necessory!");
                    if(mPassword.isEmpty())
                        passEdit.setError("password is necessory!");
                    if(!mPassword.equals(reEnteredPass))
                        reEnterPassEdit.setError("re-enter pass correctly!");
                    if(mPassword.length()<5 && mPassword.length()>0)
                        passEdit.setError("weak password!");
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Intent intent = getIntent();
        if(requestCode == GOOGLE_SIGN_IN_REQUEST_CODE){
            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                final GoogleSignInAccount signInAcc = signInTask.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(signInAcc.getIdToken(),null);
                mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignupActivity.this, "You are Successfully Signed in", Toast.LENGTH_SHORT).show();
                        Intent toProfileIntent = new Intent(SignupActivity.this, ProfileActivity.class);
                        toProfileIntent.putExtra("username",mUsername);
                        toProfileIntent.putExtra("password",mPassword);
                        toProfileIntent.putExtra("email",signInAcc.getEmail());
                        toProfileIntent.putExtra("isDriver",intent.getBooleanExtra("isDriver",false));
                        saveAccountInDatabase(mUsername,mPassword,signInAcc.getEmail(),intent.getBooleanExtra("isDriver",false));
                        startActivity(toProfileIntent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this, "Something wrong happend", Toast.LENGTH_SHORT).show();
                        Log.i("TAG", "onFailure: "+e.getMessage());
                    }
                });

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }

    }

    private void initViews(){
        signupBtn = (ImageButton)findViewById(R.id.signup_button_signup);
        userEdit = (TextInputEditText)findViewById(R.id.username_input_signup);
        passEdit = (TextInputEditText)findViewById(R.id.password_input_signup);
        reEnterPassEdit = (TextInputEditText)findViewById(R.id.reenter_password);
        googleSignInRadio = (RadioButton)findViewById(R.id.google_signup);
        emailSignInRadio = (RadioButton)findViewById(R.id.email_signup);
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
