package com.feed.feedyneedy;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FoodOrderAdapter extends RecyclerView.Adapter<FoodOrderAdapter.FoodOrderViewHolder> {

    private List<FoodOrder> foodOrderList;
    private List<Food> foods;
    private List<FoodOrder> filteredFoodOrderList = null;
    private OnItemClickListener listener = null;
    //private final List<Order> orderList;

   // public FoodOrderAdapter(List<Food> foods) {
   //     this.foods = foods;
    //}

    public interface OnItemClickListener {
        void onItemClick(FoodOrder foodOrder);
    }
    public FoodOrderAdapter(List<FoodOrder> foodOrderList) {
        this.foodOrderList = foodOrderList;
        this.filteredFoodOrderList=new ArrayList<>(foodOrderList);
        this.listener = listener;
        this.foods=foods;
        //this.orderList = orderList;
    }


    public void filter(String query) {
        getFilter().filter(query);
    }
    public void filterList(List<FoodOrder> filteredList) {
        foodOrderList = filteredFoodOrderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_order, parent, false);
        return new FoodOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodOrderViewHolder holder, int position) {
        FoodOrder foodOrder = filteredFoodOrderList.get(position);
        //Order order = orderList.get(position);
        //holder.bind(order);
        holder.bind(foodOrder,listener);
    }

    @Override
    public int getItemCount() {
        return filteredFoodOrderList.size();
        //return orderList.size();
    }

    static class FoodOrderViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewFoodName;
        private final TextView textViewQuantity;
        private final TextView textViewDonorName;
        private final TextView textViewDonorAddress;
        //private final TextView textViewReceiverName;
        private final TextView textViewReceiverAddress = null;
        private FoodAdapter.OnItemClickListener listener;

        public FoodOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFoodName = itemView.findViewById(R.id.foodNameTextView);
            textViewQuantity = itemView.findViewById(R.id.quantityTextView);
            textViewDonorName = itemView.findViewById(R.id.donorNameTextView);
            textViewDonorAddress = itemView.findViewById(R.id.donorAddressTextView);
           // textViewReceiverName = itemView.findViewById(R.id.receiverNameTextView);
            //textViewReceiverAddress = itemView.findViewById(R.id.receiverAddressTextView);
        }
        



        public void bind(FoodOrder foodOrder, final OnItemClickListener listener) {
            textViewFoodName.setText("order Id:: " + foodOrder.getOrderId());
           // textViewQuantity.setText("Quantity: " + foodOrder.getQuantity());
           // textViewDonorName.setText("Donor: " + foodOrder.getDonorName());
            textViewDonorAddress.setText("Donor Address: " + foodOrder.getSenderAddress());
            //textViewReceiverName.setText("Receiver: " + foodOrder.getReceiverName());
            textViewReceiverAddress.setText("Receiver Address: " + foodOrder.getReceiverAddress());

            itemView.setOnClickListener(view -> listener.onItemClick(foodOrder));

        }
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString().toLowerCase();

                List<FoodOrder> filteredList = new ArrayList<>();
                for (FoodOrder foodOrder : foodOrderList) {
                    if (foodOrder.getReceiverAddress().toLowerCase().contains(query)) {
                        filteredList.add(foodOrder);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }


            @SuppressLint("NotifyDataSetChanged")
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredFoodOrderList.clear();
                if (filterResults.values instanceof List<?>) {
                    filteredFoodOrderList.addAll((List<? extends FoodOrder>) filterResults.values);
                }
                notifyDataSetChanged();
            }
        };
    }
}
