package com.example.bloodmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bloodmatch.data.UserAccount;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button resetButton;
    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        resetButton = findViewById(R.id.button_reset);
        emailEditText = findViewById(R.id.email);

        resetButton.setOnClickListener( v->{
            String email = emailEditText.getText().toString();
            if( email.isEmpty() ){
                emailEditText.setError("Email cannot be empty !");
                return;
            }

            if(  !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailEditText.setError("Enter a valid email !");
                return;
            }

            UserAccount.sendPasswordReset(email).addOnCompleteListener(task->{
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this, "Reset email sent", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> {
                        finish();
                    },1000);

                }else {
                    Toast.makeText(ForgotPasswordActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}