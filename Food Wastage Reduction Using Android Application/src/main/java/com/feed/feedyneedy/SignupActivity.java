package com.feed.feedyneedy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

   private EditText editTextUsername, editTextMobile, editTextEmail, editTextPassword, editTextConfirmPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.confirmpassword);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        // Firebase Realtime Database reference
        firebaseAuth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(view -> registerUser());
    }

         private void registerUser() {
                    String username = editTextUsername.getText().toString().trim();
                    String mobile = editTextMobile.getText().toString().trim();
                    String email = editTextEmail.getText().toString().trim();
                    String password = editTextPassword.getText().toString().trim();
                    String confirmPassword = editTextConfirmPassword.getText().toString().trim();

                    if (username.isEmpty() || mobile.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                        Toast.makeText(SignupActivity.this, "all fields are required", Toast.LENGTH_SHORT).show();
                    }
                    if (!password.equals(confirmPassword)) {
                        Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        return;
                    }



             firebaseAuth.createUserWithEmailAndPassword(email, password)
                     .addOnCompleteListener(this, task -> {
                         if (task.isSuccessful()) {
                             // Registration success
                             Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                             startActivity(intent);
                             finish();
                         } else {
                             // If registration fails, display a message to the user.
                             Toast.makeText(SignupActivity.this, "Registration failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     });
         }
}