package com.feed.feedyneedy;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

public class Order extends Food implements  Parcelable {
    private String orderId;
    private String userName;
    private String userAddress;
    private String userContact;

    private String DonorName;

    public Order() {
        // Default constructor required for Firebase
    }

    public Order(String orderId, String userName, String userAddress, String userContact) {
        this.orderId = orderId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userContact = userContact;
    }
    public String getOrderId() {
        return orderId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAddress()
    {
        return userAddress;
    }

    public String  getUserContact() {
        return userContact;
    }
    public String  getDonorName() {
        return DonorName; }

    public LatLng getUserAddressAsLatLng(){
        String[] latlngArray = userAddress.split(",");

        if (latlngArray.length == 2) {
            try {
                double latitude = Double.parseDouble(latlngArray[0]);
                double longitude = Double.parseDouble(latlngArray[1]);
                return new LatLng(latitude, longitude);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Return a default LatLng or handle the error as needed
        return new LatLng(0, 0);
    }



    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("orderId", orderId);
        result.put("userName", userName);
        result.put("userAddress", userAddress);
        result.put("userContact", userContact);


        return result;
    }

    // Parcelable implementation
    protected Order(Parcel in) {
        orderId = in.readString();
        userName = in.readString();
        userAddress = in.readString();
        userContact = in.readString();
        DonorName = in.readString();

    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(userName);
        dest.writeString(userAddress);
        dest.writeString(userContact);

    }


}