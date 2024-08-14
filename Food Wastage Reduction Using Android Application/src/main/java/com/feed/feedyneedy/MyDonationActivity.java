package com.feed.feedyneedy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyDonationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<Food> myDonations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donation);

        // Initialize UI components
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myDonations = new ArrayList<>();
        foodAdapter = new FoodAdapter(myDonations, this::onFoodClick);
        recyclerView.setAdapter(foodAdapter);

        // Retrieve user-specific foods from Firebase Realtime Database
        String currentUserId = getCurrentUserId(); // You need to implement getCurrentUserId() method
        DatabaseReference foodsRef = FirebaseDatabase.getInstance().getReference("foods");

        foodsRef.orderByChild("addedByUserId").equalTo(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myDonations.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Food food = snapshot.getValue(Food.class);
                    myDonations.add(food);
                }
                foodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void onFoodClick(Food food) {
        // Handle food click, you can navigate to a details page or perform other actions
        String orderedByOrderId = food.getOrderedByOrderId();
        if (orderedByOrderId != null) {
            // Food has been ordered, you can navigate to the order details page
            navigateToOrderDetails(orderedByOrderId);
        } else {
            // Food has not been ordered yet
            // You can implement the logic for deletion or other actions
        }
    }

    private void navigateToOrderDetails(String orderId) {
        // Implement navigation to order details activity
    }

    // Implement getCurrentUserId() method to get the current user's ID
    private String getCurrentUserId() {
        // Add your implementation to get the current user's ID
        return "user123"; // Replace with your actual implementation
    }
}
