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

import com.example.bloodmatch.data.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView registerNowText, forgotPasswordTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        // check  if user is alreadyLogged
        isAlreadyLogged();

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.button_login);
        registerNowText = findViewById(R.id.register_now);
        forgotPasswordTextView = findViewById(R.id.forget_password);

        registerNowText.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this , RegisterActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        forgotPasswordTextView.setOnClickListener(v->{
            Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                if(email.isEmpty()){
                    emailInput.setError("Please enter your email !");
                    emailInput.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    passwordInput.setError("Please enter your password !");
                    return;
                }

                UserAccount.signIn(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Incorrect email or password !", Toast.LENGTH_SHORT).show();
                            forgotPasswordTextView.setVisibility(View.VISIBLE);
                        }else{
                            UpdateUI();
                        }
                    }
                });
            }
        });
    }

    private void isAlreadyLogged(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            UpdateUI();
        }
    }

    public void UpdateUI(){
        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}