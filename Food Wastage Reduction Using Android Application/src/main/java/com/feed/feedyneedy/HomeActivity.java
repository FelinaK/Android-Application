package com.feed.feedyneedy;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

    public class HomeActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            Button foodItemsButton = findViewById(R.id.foodItemsButton);
            Button clothesButton = findViewById(R.id.clothesButton);
            Button groceryButton = findViewById(R.id.groceryButton);
            Button stationaryButton = findViewById(R.id.stationaryButton);
            Button pharmacyButton = findViewById(R.id.pharmacyButton);

            foodItemsButton.setOnClickListener(v -> openActivity());
            clothesButton.setOnClickListener(v -> openActivity());
            groceryButton.setOnClickListener(v -> openActivity());
            stationaryButton.setOnClickListener(v -> openActivity());
            pharmacyButton.setOnClickListener(v -> openActivity());
        }

        private void openActivity() {
            Intent intent = new Intent(HomeActivity.this, FoodItemsActivity.class);
            startActivity(intent);
        }
    }
