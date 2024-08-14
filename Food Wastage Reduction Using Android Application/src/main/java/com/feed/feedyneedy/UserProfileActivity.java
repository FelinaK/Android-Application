package com.feed.feedyneedy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference userReference;

    private TextView displayNameTextView;
    private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        Button Donation= findViewById(R.id.mydonation);
       // Button Orders=findViewById(R.id.myorder);
       // Button Deliver=findViewById(R.id.mydeliver);


        Donation.setOnClickListener(v ->  {
            startActivity(new Intent(UserProfileActivity.this, MyDonationActivity.class));
        });

        if (currentUser == null) {
            // If the user is not authenticated, redirect to the login activity
            startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
            finish();
            return;
        }

        // Initialize views
        displayNameTextView = findViewById(R.id.displayNameTextView);
        signOutButton = findViewById(R.id.signOutButton);

        // Initialize Firebase references
        userReference = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());

        // Load and display user data
        loadUserData();

        // Set click listener for sign out button
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void loadUserData() {
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String displayName = dataSnapshot.child("displayName").getValue(String.class);
                    displayNameTextView.setText("Welcome, " + displayName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }


     //Orders.setOnClickListener(v -> {
      //  startActivity(new Intent(UserProfileActivity.this, MyOrdersActivity.class));
   // });

    private void signOut() {
        auth.signOut();
        startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
        finish();
    }
}

