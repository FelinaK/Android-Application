package com.feed.feedyneedy;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.feed.feedyneedy.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlaceOrderActivity extends AppCompatActivity {

    private EditText editTextUserName;
    private EditText editTextUserAddress;
    private EditText editTextUserContact;
    private FirebaseAuth pAuth;

    // Reference to the Firebase Realtime Database
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        // Initialize UI components
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextUserAddress = findViewById(R.id.editTextUserAddress);
        editTextUserContact = findViewById(R.id.editTextUserContact);
        Button buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);

        pAuth = FirebaseAuth.getInstance();
        // Initialize Firebase database reference

        ordersRef = FirebaseDatabase.getInstance().getReference("orders");

        buttonPlaceOrder.setOnClickListener(view -> placeOrder());
    }

    private void placeOrder() {
        // Get user input
        String userName = editTextUserName.getText().toString().trim();
        String userAddress = editTextUserAddress.getText().toString().trim();
        String userContact = editTextUserContact.getText().toString().trim();

        Log.d("placeOrderActivity","user Name: "+userName);
        Log.d("PlaceOrderActivity","User address: "+userAddress);
        Log.d("PlaceOrderActivity","User Contact: "+userContact);

        // Validate user input
        if (userName.isEmpty() || userAddress.isEmpty() || userContact.isEmpty()) {
            Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show();
            return;
        }


        // Create a unique key for the order
        String orderId = ordersRef.push().getKey();

        Log.d("PlaceOrderactivity","Order ID: " +orderId);

        // Create an Order object
        Order order = new Order(orderId, userName, userAddress, userContact);

        // Save the order to the database
        ordersRef.child(orderId).setValue(order);

        Log.d("PlaceOrderActivity","Order placed successfully!");

        // Display a confirmation message
        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();

        // TODO: You may navigate to a confirmation page or perform other actions as needed
    }
}