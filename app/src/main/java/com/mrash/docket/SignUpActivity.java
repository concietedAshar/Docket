package com.mrash.docket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class SignUpActivity extends AppCompatActivity {


    FirebaseAuth mAuth;

    ImageView imgLogo;
    TextView tvWelcome;
    TextView tvSlogan;
    TextInputLayout tvlFullName,tvlEmail,tvlPassword;
    Button btnSignUp,btnAlreadyUser;

    //Firebase Objects
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Initializing view of xml file
        init();

        //goto login activity
        setBtnAlreadyUser();

        //signUp Button
        setBtnSignUp();

        mAuth = FirebaseAuth.getInstance();



    }


    public void setBtnSignUp() {

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validateName() | !validateEmail() | !validatePassword())
                {
                    return;
                }

                String name = tvlFullName.getEditText().getText().toString();
                String email = tvlEmail.getEditText().getText().toString();
                String password = tvlPassword.getEditText().getText().toString();


                //creating auser with email and password
                mAuth.createUserWithEmailAndPassword(email,password).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
                                    Toast.makeText(SignUpActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivity.this,com.mrash.docket.LoginActivity.class));
                                }
                                else
                                {
                                    Toast.makeText(SignUpActivity.this, "Registered Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                ); //mAuth end here

            }//view on click
        });// button sign up end

    }

    // Already User
    public void setBtnAlreadyUser() {

        btnAlreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this,com.mrash.docket.LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    //views attaching
    public void init() {
        imgLogo = findViewById(R.id.splash_logo); //1
        tvWelcome = findViewById(R.id.welcome); //2
        tvSlogan = findViewById(R.id.slogan); //3
        tvlFullName = findViewById(R.id.full_name);
        tvlEmail = findViewById(R.id.email); //4
        tvlPassword = findViewById(R.id.password); //5
        btnSignUp = findViewById(R.id.btnSignUp); //6
        btnAlreadyUser = findViewById(R.id.btnAlreadyUser); //7
    }

    //validation fields
    private boolean validateName() {
        String val = tvlFullName.getEditText().getText().toString();

        if(val.isEmpty())
        {
            tvlFullName.setError("Field can't be empty");
            return false;
        }
        else
        {
            tvlFullName.setError(null);
            tvlFullName.setErrorEnabled(false);
            return true;
        }

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


}