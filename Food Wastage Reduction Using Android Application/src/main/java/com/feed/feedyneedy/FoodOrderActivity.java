package com.feed.feedyneedy;

import android.os.Bundle;
import android.util.Log;
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

public class FoodOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FoodOrderAdapter foodOrderAdapter;
    private List<FoodOrder> foodOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        foodOrders = new ArrayList<>();
        foodOrderAdapter = new FoodOrderAdapter(foodOrders);
        recyclerView.setAdapter(foodOrderAdapter);

        // Retrieve data from Firebase Realtime Database
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("food_orders");
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodOrders.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodOrder foodOrder = snapshot.getValue(FoodOrder.class);
                    if (foodOrder != null) {
                        foodOrders.add(foodOrder);
                    }
                }
                foodOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FoodOrderActivity", "Failed to retrieve data", databaseError.toException());
            }
        });
    }
}

