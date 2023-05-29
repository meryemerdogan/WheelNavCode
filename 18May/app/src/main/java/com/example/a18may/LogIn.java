package com.example.a18may;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
- Some user related codes are derives from firebase.google.com and altered for compatibility
 */
public class LogIn extends AppCompatActivity {

    TextInputEditText email, password;
    Button LogButton;
    FirebaseAuth mAuth;
    ProgressBar prgBar;
    TextView passTo, forgotPass, contGuess;

    @Override
    public void onStart() {
        super.onStart();
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
        setContentView(R.layout.activity_log_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        LogButton = findViewById(R.id.login_btn);
        prgBar = findViewById(R.id.progressBar);
        passTo = findViewById(R.id.passToRegister);
        forgotPass = findViewById(R.id.forgotPassword);
        contGuess = findViewById(R.id.continueGuest);

        contGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Add intent to direct the user to the map activity without an account
            }
        });

        passTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
                finish();
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(i);
                finish();
            }
        });

        LogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prgBar.setVisibility(View.VISIBLE);
                String mail, pass;
                mail = String.valueOf(email.getText());
                pass = String.valueOf(password.getText());
                mAuth = FirebaseAuth.getInstance();

                if(TextUtils.isEmpty(mail)){
                    Toast.makeText(LogIn.this, "Enter your mail address", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(LogIn.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(mail, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                prgBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getApplicationContext(), "LogIn successful", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LogIn.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}