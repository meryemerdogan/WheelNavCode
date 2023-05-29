package com.example.a18may;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/*
- Some user related codes are derives from firebase.google.com and altered for compatibility
 */
public class ForgotPassword extends AppCompatActivity {

    TextInputEditText mail;
    Button sendMail;
    ProgressBar bar;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mail = findViewById(R.id.email);
        sendMail = findViewById(R.id.resetPwd_btn);
        bar = findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    public void resetPassword(){

        String email = String.valueOf(mail.getText());
        bar.setVisibility(View.VISIBLE);

        if(email.isEmpty()){
            Toast.makeText(this, "Enter an email", Toast.LENGTH_SHORT).show();
            bar.setVisibility(View.GONE);
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "No account found with the provided email", Toast.LENGTH_SHORT).show();
            bar.setVisibility(View.GONE);
            return;
        }
        else{
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    bar.setVisibility(View.GONE);
                    if(task.isSuccessful()){
                        Toast.makeText(ForgotPassword.this, "Email sent", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), LogIn.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(ForgotPassword.this, "Problem occurred, try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}