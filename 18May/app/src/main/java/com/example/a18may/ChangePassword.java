package com.example.a18may;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/*
- Some user related codes are derived from firebase.google.com and altered for compatibility
 */
public class ChangePassword extends AppCompatActivity {

    TextInputEditText oldPassword, password, password2;
    TextView goBack;
    Button updateButton;
    ProgressBar prgBar;
    FirebaseAuth auth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        oldPassword = findViewById(R.id.oldPassword);
        updateButton = findViewById(R.id.update_btn);
        prgBar = findViewById(R.id.progressBar);
        goBack = findViewById(R.id.go_back);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prgBar.setVisibility(View.VISIBLE);
                String psw = String.valueOf(password.getText());
                String psw2 = String.valueOf(password2.getText());
                String old = String.valueOf(oldPassword.getText());
                if(TextUtils.isEmpty(psw) || TextUtils.isEmpty(psw2) || TextUtils.isEmpty(old)){
                    Toast.makeText(ChangePassword.this, "Fill all the necessary spaces", Toast.LENGTH_SHORT).show();
                    prgBar.setVisibility(View.GONE);
                    return;
                }
                else{
                    if(psw.equals(psw2)){
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(currentUser.getEmail(), old);

                        if(currentUser.reauthenticate(credential).isSuccessful()){

                        }

                        currentUser.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            currentUser.updatePassword(psw)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(ChangePassword.this, "Password updated", Toast.LENGTH_SHORT).show();
                                                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                                startActivity(i);
                                                                finish();
                                                                prgBar.setVisibility(View.GONE);
                                                            }
                                                            else{
                                                                Toast.makeText(ChangePassword.this, "Update failed", Toast.LENGTH_SHORT).show();
                                                                prgBar.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    });
                                        }
                                        else{
                                            Toast.makeText(ChangePassword.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                            prgBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                        prgBar.setVisibility(View.GONE);
                        Toast.makeText(ChangePassword.this, "Password updated", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(ChangePassword.this, "Your passwords are not matching!", Toast.LENGTH_SHORT).show();
                        prgBar.setVisibility(View.GONE);
                    }
                }

            }
        });
    }
}