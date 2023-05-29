package com.example.a18may;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.Timer;
import java.util.TimerTask;

/*
- Some user related codes are derived from firebase.google.com and altered for compatibility
 */
public class MainActivity extends AppCompatActivity {

    Button logOutButton, proceedButton, changePasswordButton;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView userMail, userID, username;
    String name;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        logOutButton = findViewById(R.id.logOut_btn);
        proceedButton = findViewById(R.id.proceed_btn);
        changePasswordButton = findViewById(R.id.changePassword_btn);
        username = findViewById(R.id.username);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            Intent i = new Intent(getApplicationContext(), LogIn.class);
            startActivity(i);
            finish();
        }
        else{
            for (UserInfo profile : user.getProviderData()) {
                // Name, email address, and profile photo Url
                name = profile.getDisplayName();

            }
            if(name == null){
                Timer timer = new Timer();
                TimerTask checkName = new TimerTask() {
                    @Override
                    public void run() {
                        if(name != null){
                            timer.cancel();
                            username.setText("username: " + name);
                        }
                        else{
                            username.setText("username waiting");
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                        }
                    }
                };
                timer.scheduleAtFixedRate(checkName, 0, 2500);
            }
            username.setText("username: " + name);



            logOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(getApplicationContext(), LogIn.class);
                    startActivity(i);
                    finish();
                }
            });
            userMail = findViewById(R.id.userMail);
            userMail.setText("email: " + user.getEmail());
            userID = findViewById(R.id.userID);
            userID.setText("ID: " + user.getUid());

            changePasswordButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), ChangePassword.class);
                    startActivity(i);
                    finish();
                }
            });
            proceedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), SelectRoadsActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
    }
}