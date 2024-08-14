package com.feed.feedyneedy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;
import java.util.Objects;

public class AddFoodActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageViewFood;
    private EditText editTextFoodName, editTextQuantity, editTextDonorName, editTextDonorAddress, editTextDonorContact;
    private Uri imageUri;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("foods");
        mStorage = FirebaseStorage.getInstance().getReference("food_images");

        imageViewFood = findViewById(R.id.imageViewFood);
        editTextFoodName = findViewById(R.id.editTextFoodName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextDonorName = findViewById(R.id.editTextDonorName);
        editTextDonorAddress = findViewById(R.id.editTextDonorAddress);
        editTextDonorContact = findViewById(R.id.editTextDonorContact);

        Button buttonChooseImage = findViewById(R.id.buttonChooseImage);
        Button buttonAddFood = findViewById(R.id.buttonAddFood);

        buttonChooseImage.setOnClickListener(v -> openImageChooser());

        buttonAddFood.setOnClickListener(v -> addFoodToDatabase());
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageViewFood.setImageURI(imageUri);
        }
    }
    private void addFoodToDatabase() {
        final String foodName = editTextFoodName.getText().toString().trim();
        final String quantity = editTextQuantity.getText().toString().trim();
        final String donorName = editTextDonorName.getText().toString().trim();
        final String donorAddress = editTextDonorAddress.getText().toString().trim();
        final String donorContact = editTextDonorContact.getText().toString().trim();

        // Validate input fields
        if (foodName.isEmpty() || quantity.isEmpty() || donorName.isEmpty() || donorAddress.isEmpty() || donorContact.isEmpty() || imageUri == null) {
            Toast.makeText(AddFoodActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Upload image to Firebase Storage
        final StorageReference imageRef = mStorage.child(Objects.requireNonNull(imageUri.getLastPathSegment()));
        imageRef.putFile(imageUri).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    // Image uploaded successfully, now add data to Realtime Database
                    Food food = new Food(foodName, quantity, donorName, donorAddress, donorContact, uri.toString());

                    // Convert Food object to Map
                    Map<String, Object> foodValues = food.toMap();

                    // Save data to Realtime Database
                    String foodId = mDatabase.push().getKey();
                    mDatabase.child(foodId).updateChildren(foodValues);

                    Toast.makeText(AddFoodActivity.this, "Food added successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddFoodActivity.this, FoodItemsActivity.class));
                }).addOnFailureListener(e -> {
                    // Handle failure to get download URL
                    Toast.makeText(AddFoodActivity.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                });
            } else {
                // Handle failure to upload image
                Toast.makeText(AddFoodActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            }
        });
    }

}