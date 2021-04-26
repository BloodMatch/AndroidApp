package com.example.bloodmatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText userEmail;
    private EditText userPassword;
    private Button registerButton;
    private TextView tvSignIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        mAuth = FirebaseAuth.getInstance();
        userEmail = findViewById(R.id.email_input);
        userPassword = findViewById(R.id.password_input);
        registerButton = findViewById(R.id.button_register);
        tvSignIn = findViewById(R.id.signIn);
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();
                if(email.isEmpty()){
                    userEmail.setError("Please enter your email !");
                    userEmail.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    userPassword.setError("Please enter your password !");
                    return;
                }
                // For registration
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(Register.this, "Message pour le user", Toast.LENGTH_SHORT).show();
                        }else{
                            startActivity(new Intent(Register.this, LoginActivity.class));
                        }
                    }
                });
            }
        });
    }
}