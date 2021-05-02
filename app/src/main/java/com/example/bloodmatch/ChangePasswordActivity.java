package com.example.bloodmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bloodmatch.data.UserAccount;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText currentPasswordEditText, newPasswordEditText, confirmPasswordEditText;
    private Button resetButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        currentPasswordEditText = findViewById(R.id.currentPassword);
        newPasswordEditText = findViewById(R.id.newPassword);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        resetButton = findViewById(R.id.button_reset);

        resetButton.setOnClickListener(v->{
            String currentPassword, newPassword, confirmPassword;

            currentPassword = currentPasswordEditText.getText().toString();
            if( currentPassword.isEmpty() ){
                currentPasswordEditText.setError("Please enter your password cannot be empty !");
                return;
            }



            newPassword = newPasswordEditText.getText().toString();
            if( newPassword.isEmpty() ){
                newPasswordEditText.setError("New password cannot be empty !");
                return;
            }

            if( newPassword.length() < 6 ){
                newPasswordEditText.setError("The new password must be at least 6 characters long !");
                return;
            }

            confirmPassword = confirmPasswordEditText.getText().toString();
            if( confirmPassword.isEmpty() ){
                confirmPasswordEditText.setError("Confirm password cannot be empty !");
                return;
            }

            if( !confirmPassword.equals(newPassword) ){
                confirmPasswordEditText.setError("The confirm password confirmation does not match !");
                return;
            }

            UserAccount.updatePassword(newPassword).addOnCompleteListener(task->{
                if(task.isSuccessful()){
                    Toast.makeText(ChangePasswordActivity.this, "Password changed", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> {
                        finish();
                    },1000);

                }else {
                    Toast.makeText(ChangePasswordActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}