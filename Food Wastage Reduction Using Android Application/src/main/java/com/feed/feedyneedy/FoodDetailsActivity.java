package com.feed.feedyneedy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;


public class FoodDetailsActivity extends AppCompatActivity {
    private Food food;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        // Get the Food object from the Intent
        food = getIntent().getParcelableExtra("food");

        // Display details in TextViews (Replace with your actual layout)
        TextView foodNameTextView = findViewById(R.id.foodNameTextView);
        TextView donorDetailsTextView = findViewById(R.id.donorNameTextView);
        Button placeOrderButton = findViewById(R.id.placeOrderButton);
        Button ViewLocation = findViewById(R.id.buttonViewLocation);
        ImageView imageViewFood = findViewById(R.id.imageViewFood);

        foodNameTextView.setText("Food Name: " + food.getFoodName());
        donorDetailsTextView.setText("Donor: " + food.getDonorName() + "\n"
                + "Address: " + food.getDonorAddress() + "\n"
                + "Contact: " + food.getDonorContact());
        placeOrderButton.setOnClickListener(v -> {
            Intent intent = new Intent(FoodDetailsActivity.this, PlaceOrderActivity.class);
            startActivity(intent);
        });
        Picasso.get().load(food.getImageUrl()).placeholder(R.drawable.placeholder_image).into(imageViewFood);

        // ViewLocation.setOnClickListener(v1 -> {
        //    Intent intent1=new Intent(FoodDetailsActivity.this,GeoLocation.class);
        //  startActivity(intent1);

    }

    public void viewLocationOnMap(View view) {
        // Get the donor's address from the current Food object
        if (food != null) {
            String donorAddress = food.getDonorAddress();

            // Create a Uri from the donor's address
            Uri locationUri = Uri.parse("geo:0,0?q=" + Uri.encode(donorAddress));

            // Create an Intent to open Google Maps
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            // Check if there is an app to handle the intent
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                // Start the activity to open Google Maps
                startActivity(mapIntent);
            } else {
                // Handle the case where Google Maps is not installed
                Toast.makeText(this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
