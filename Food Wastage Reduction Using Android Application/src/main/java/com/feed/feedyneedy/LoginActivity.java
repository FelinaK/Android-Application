package com.feed.feedyneedy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.emailEditText);
        editTextPassword = findViewById(R.id.passwordEditText);

        Button buttonLogin = findViewById(R.id.loginButton);
        Button buttonSignup = findViewById(R.id.signupButton);

        TextView textViewForgotPassword = findViewById(R.id.forgotpassword);

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, FoodItemsActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        buttonSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        textViewForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });
    }
}
