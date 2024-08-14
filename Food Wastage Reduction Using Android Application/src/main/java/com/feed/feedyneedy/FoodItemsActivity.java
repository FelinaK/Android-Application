package com.feed.feedyneedy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FoodItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items);

        Button buttonAddFoodItem = findViewById(R.id.buttonAddFoodItem);
        Button buttonViewFoodItems = findViewById(R.id.buttonViewFoodItems);
        Button buttonDeliver=findViewById(R.id.buttonDeliver);
        TextView home = findViewById(R.id.pagename);

        home.setOnClickListener(v -> {
            startActivity(new Intent(FoodItemsActivity.this, UserProfileActivity.class));
        });

        buttonAddFoodItem.setOnClickListener(v -> {
            // Move to AddFoodActivity
            startActivity(new Intent(FoodItemsActivity.this, AddFoodActivity.class));
        });

        buttonViewFoodItems.setOnClickListener(v -> {
            // Move to ViewFoodActivity
            startActivity(new Intent(FoodItemsActivity.this, ViewFoodActivity.class));
        });

       buttonDeliver.setOnClickListener(v -> {
           //Move to DeliverFoodActivity
           startActivity(new Intent(FoodItemsActivity.this, DeliverFoodActivity.class));
       });
    }
}
