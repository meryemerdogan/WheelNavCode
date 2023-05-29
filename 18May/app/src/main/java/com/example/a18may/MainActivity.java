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
/*
- Some user related codes are derives from firebase.google.com and altered for compatibility
 */
public class MainActivity extends AppCompatActivity {

    Button logOutButton, proceedButton, savedRoutesButton, changePasswordButton;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView userMail, userID, username;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        logOutButton = findViewById(R.id.logOut_btn);
        proceedButton = findViewById(R.id.proceed_btn);
        savedRoutesButton = findViewById(R.id.savedRoutes_btn);
        changePasswordButton = findViewById(R.id.changePassword_btn);
        username = findViewById(R.id.username);

        user = auth.getCurrentUser();

        if(user == null){
            Intent i = new Intent(getApplicationContext(), LogIn.class);
            startActivity(i);
            finish();
        }
        else{
            for (UserInfo profile : user.getProviderData()) {
                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                username.setText(name);
            }
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