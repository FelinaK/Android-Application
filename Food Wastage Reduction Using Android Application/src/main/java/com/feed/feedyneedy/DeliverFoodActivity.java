package com.feed.feedyneedy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
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

public class DeliverFoodActivity extends AppCompatActivity {

    private FoodOrderAdapter foodOrderAdapter;
    private List<FoodOrder> orderedFoods;
    private List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_food);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderedFoods = new ArrayList<>();
        foodOrderAdapter = new FoodOrderAdapter(orderedFoods);



        recyclerView.setAdapter(foodOrderAdapter);

        // Retrieve data from Firebase Realtime Database
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderedFoods.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodOrder orderFood = snapshot.getValue(FoodOrder.class);
                    orderedFoods.add(orderFood);
                }
                foodOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
    private void showOrderDetails(FoodOrder orderedfoods) {

        Intent intent = new Intent(DeliverFoodActivity.this, DeliveryDetailsActivity.class);
        intent.putExtra("orderedfoods", String.valueOf(orderedfoods));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchOrders(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    searchOrders(newText);
                    return true;
                }
            });
        }

        return true;
    }

    String donorAddress = null;

    private void searchOrders(String query) {
        if (!TextUtils.isEmpty(query)) {
            List<FoodOrder> filteredList = new ArrayList<>();
            for (com.feed.feedyneedy.FoodOrder FoodOrder : orderedFoods) {
                // Assuming you have a method to get donor address
                donorAddress = FoodOrder.getSenderAddress();

                // Check if the donor address is not null before performing operations
                if (donorAddress != null) {
                    // Check if the query matches donor address
                    if (donorAddress.toLowerCase().contains(query.toLowerCase())) {
                        filteredList.add(FoodOrder);
                    }
                }
            }
          foodOrderAdapter.filterList(filteredList);
        } else {
            foodOrderAdapter.filterList(orderedFoods);
        }
    }



        // Display details and navigate to the next activity
        // Implement this based on your requirements

}
