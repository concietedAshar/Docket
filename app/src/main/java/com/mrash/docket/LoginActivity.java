package com.mrash.docket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    ImageView imgLogo;
    TextView tvWelcome;
    TextView tvSlogan;
    TextInputLayout tvlEmail,tvlPassword;
    Button btnForgetPass,btnSignIn,btnNewUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //attach views //hooks
        init();
        //new user button
        btnNewUser();

        setBtnSignIn();

        mAuth = FirebaseAuth.getInstance();




    }

    public void setBtnSignIn(){

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validateEmail() | !validatePassword())
                {
                    return;
                }

                String email = tvlEmail.getEditText().getText().toString();
                String password = tvlPassword.getEditText().getText().toString();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this, "User Login Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,com.mrash.docket.DashBoard.class));
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Login Error : ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

    }


    //new user button to goto signup screen
    private void btnNewUser()
    {

        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this,com.mrash.docket.SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //to attach views
    private void init()
    {
        imgLogo = findViewById(R.id.splash_logo); //1
        tvWelcome = findViewById(R.id.welcome);//2
        tvSlogan = findViewById(R.id.slogan);//3
        tvlEmail = findViewById(R.id.email);//4
        tvlPassword = findViewById(R.id.password);//5
        btnForgetPass = findViewById(R.id.btnForgetPass);
        btnSignIn = findViewById(R.id.btnSignIn); //6
        btnNewUser = findViewById(R.id.btnNewUser);//7
    }

    private boolean validateEmail() {
        String val = tvlEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty())
        {
            tvlEmail.setError("Field can't be empty");
            return false;
        }
        else if(!val.matches(emailPattern))
        {
            tvlEmail.setError("Invalid Email Address");
            return false;
        }
        else
        {
            tvlEmail.setError(null);
            tvlEmail.setErrorEnabled(false);
            return true;
        }

    }
    private boolean validatePassword() {
        String val = tvlPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[a-zA-Z])" + //any letter
                "(?=.*[@#$%^&+=])" + //at least 1 special
                "(?=\\S+$)" + // no white spaces
                ".{6,}" + "$"; // at least 6 character

        if(val.isEmpty())
        {
            tvlPassword.setError("Field can't be empty");
            return false;
        }
        else if(!val.matches(passwordVal))
        {
            tvlPassword.setError("Invalid Password");
            return false;
        }
        else
        {
            tvlPassword.setError(null);
            return true;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser != null)
        {
            startActivity(new Intent(LoginActivity.this,com.mrash.docket.DashBoard.class));

        }
    }
}