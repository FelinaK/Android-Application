package com.feed.feedyneedy;

import androidx.annotation.NonNull;
import com.feed.feedyneedy.Order;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodOrder {
    private String orderId;
    private String senderAddress;
    private String receiverAddress;

    // Existing constructor and other methods...

    // Method to map the data from DataSnapshot to FoodOrder
    public static FoodOrder fromSnapshot(DataSnapshot snapshot) {
        FoodOrder foodOrder = new FoodOrder();
        foodOrder.orderId = snapshot.getKey(); // Assuming orderId is the key
        foodOrder.senderAddress = (String) snapshot.child("senderAddress").getValue();
        foodOrder.receiverAddress = (String) snapshot.child("receiverAddress").getValue();
        // Map other fields similarly

        return foodOrder;
    }

    // Exclude certain properties when converting to a map for updating in the database
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("orderId", orderId);
        result.put("senderAddress", senderAddress);
        result.put("receiverAddress", receiverAddress);

        // Add other fields similarly

        return result;
    }


    // Setter and getter methods...
    public String getSenderAddress() {
        return senderAddress;
    }
    public String getOrderId() {
        return orderId;
    }
    public String getReceiverAddress() {
        return receiverAddress;
    }

    // Method for retrieving data from the database
    public static void getFoodOrders(FoodOrderCallback callback) {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
        List<FoodOrder> foodOrders = new ArrayList<>();

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodOrders.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodOrder foodOrder = FoodOrder.fromSnapshot(snapshot);
                    foodOrders.add(foodOrder);
                }
                callback.onFoodOrdersRetrieved(foodOrders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }

    // Callback interface to handle the retrieved data
    public interface FoodOrderCallback {
        void onFoodOrdersRetrieved(List<FoodOrder> foodOrders);
        void onError(DatabaseError databaseError);
    }
}
