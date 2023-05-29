package com.example.a18may;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
/*
- Some user related codes are derived from firebase.google.com and altered for compatibility
 */
public class Register extends AppCompatActivity {

    TextInputEditText email, password, password2, userName;
    Button registerButton;
    FirebaseAuth mAuth;

    ProgressBar prgBar;

    TextView passTo;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        registerButton = findViewById(R.id.register_btn);
        prgBar = findViewById(R.id.progressBar);
        passTo = findViewById(R.id.passToLogIn);
        userName = findViewById(R.id.username);

        passTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LogIn.class);
                startActivity(i);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prgBar.setVisibility(View.VISIBLE);
                String mail, pass, pass2, userNameString;
                mail = email.getText().toString();
                pass = password.getText().toString();
                pass2 = password2.getText().toString();

                userNameString = userName.getText().toString();

                if(TextUtils.isEmpty(mail)){
                    Toast.makeText(Register.this, "Enter your mail address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(pass) || TextUtils.isEmpty(pass2)){
                    Toast.makeText(Register.this, "Enter your password twice", Toast.LENGTH_SHORT).show();
                    prgBar.setVisibility(View.GONE);
                    return;
                }
                if(pass.equals(pass2)){
                    mAuth.createUserWithEmailAndPassword(mail, pass)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mAuth = FirebaseAuth.getInstance();
                                        prgBar.setVisibility(View.GONE);
                                        Toast.makeText(Register.this, "Account created!",
                                                Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(userNameString)
                                                .build();

                                        user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                        }
                                                    }
                                                });
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                        finish();

                                    } else {
                                        prgBar.setVisibility(View.GONE);
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Register.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(Register.this, "Passwords need to match!", Toast.LENGTH_SHORT).show();
                    prgBar.setVisibility(View.GONE);
                }

            }
        });

    }
}