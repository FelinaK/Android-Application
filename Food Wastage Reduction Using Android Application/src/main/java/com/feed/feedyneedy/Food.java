package com.feed.feedyneedy;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

public class Food implements Parcelable {
    private String foodName;
    private String quantity;
    private String donorName;
    private String addedByUserId;
    private String orderedByOrderId;
    private String donorAddress;
    private String name ;
    private String description;
    private String donorContact;
    private String imageUrl;

    public Food() {
        // Default constructor required for calls to DataSnapshot.getValue(Food.class)
    }
    public Food(String Name, String description, String addedByUserId, String orderedByOrderId) {
        // Initialize other fields
        this.name = name;
        this.description=description;
        this.addedByUserId = addedByUserId;
        this.orderedByOrderId = orderedByOrderId;
    }

    public Food(String foodName, String quantity, String donorName, String donorAddress, String donorContact, String imageUrl) {
        this.foodName = foodName;
        this.quantity = quantity;
        this.donorName = donorName;
        this.donorAddress = donorAddress;
        this.donorContact = donorContact;
        this.imageUrl = imageUrl;
    }
   public String getaddedByUserID(){
        return addedByUserId;
   }
   public String getOrderedByOrderId(){
        return orderedByOrderId;
   }
    public String getFoodName() {
        return foodName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDonorName() {
        return donorName;
    }

    public String getDonorAddress() {
        return donorAddress;
    }

    public String getDonorContact() {
        return donorContact;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("foodName", foodName);
        result.put("quantity", quantity);
        result.put("donorName", donorName);
        result.put("donorAddress", donorAddress);
        result.put("donorContact", donorContact);
        result.put("imageUrl", imageUrl);

        return result;
    }

    // Parcelable implementation
    protected Food(Parcel in) {
        foodName = in.readString();
        quantity = in.readString();
        donorName = in.readString();
        donorAddress = in.readString();
        donorContact = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(foodName);
        dest.writeString(quantity);
        dest.writeString(donorName);
        dest.writeString(donorAddress);
        dest.writeString(donorContact);
        dest.writeString(imageUrl);
    }


    public String getReceiverAddress() {
        return null;
    }
}