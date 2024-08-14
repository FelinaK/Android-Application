package com.feed.feedyneedy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewFoodActivity extends AppCompatActivity {

    private FoodAdapter foodAdapter;
    private List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        foodList = new ArrayList<>();
        // Handle item click, for example, show details and navigate to place order activity
        foodAdapter = new FoodAdapter(foodList, this::showFoodDetails);

        recyclerView.setAdapter(foodAdapter);


        // Retrieve data from Firebase Realtime Database
        DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference("foods");
        foodRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Food food = snapshot.getValue(Food.class);
                    foodList.add(food);
                }
                foodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }


    private void showFoodDetails(Food food) {
        // Display details and navigate to place order activity
        Intent intent = new Intent(ViewFoodActivity.this, FoodDetailsActivity.class);
        intent.putExtra("food", food);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        if(searchView!=null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Perform search based on the query
                    searchFood(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Perform search as the user types
                    searchFood(newText);
                    return true;
                }
            });
        }

        return true;
    }

    private void searchFood(String query) {
        // Implement search logic here based on the query
        // You can filter the foodList and update the RecyclerView
        // For simplicity, assume the foodList contains all data
        // Update the adapter with the filtered list
        foodAdapter.filter(query);
    }
}