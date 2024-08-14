package com.feed.feedyneedy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder> {

    private final List<Order> foodOrders;

    private final List<Food> donor;
    private final List<Order> filteredList;

    public DeliveryAdapter(List<Order> foodOrders,List<Food> donor) {
        this.foodOrders = foodOrders;
        this.donor=donor;
        this.filteredList = new ArrayList<>(foodOrders);
    }

    @NonNull
    @Override
    public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_item, parent, false);
        return new DeliveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryViewHolder holder, int position) {
        Order foodOrder = filteredList.get(position);
        Food donor=filteredList.get(position);
        holder.bind(foodOrder,donor);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }
    public void filterList(List<Order> filteredList) {
        this.filteredList.clear();
        this.filteredList.addAll(filteredList);
        notifyDataSetChanged();
    }

    static class DeliveryViewHolder extends RecyclerView.ViewHolder {

        private final TextView orderDetailsTextView;

        public DeliveryViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDetailsTextView = itemView.findViewById(R.id.textViewOrderDetails);
        }

        public void bind(final Order foodOrder,final Food donor) {
            String orderDetails = "Order ID: " + foodOrder.getOrderId() + "\n"
                    + "Receiver: " + foodOrder.getUserName()+"\n"
                   + "Receiver Address: " + foodOrder.getUserAddress() + "\n"
                    + "Receiver contact : " + foodOrder.getUserContact() + "\n"
                    + "Donor: " + donor.getDonorName() + "\n"
                    + "Donor Address: " + donor.getDonorAddress() + "\n"
                    +"Donor Contact:" +donor.getDonorContact();

            orderDetailsTextView.setText(orderDetails);
        }


    }

}