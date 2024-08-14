package com.feed.feedyneedy;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> implements Filterable {

    private List<Food> foodList;
    private final List<Food> filteredFoodList;
    private final OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(Food food);
    }

    public FoodAdapter(List<Food> foodList, OnItemClickListener listener) {
        this.foodList = foodList;
        this.filteredFoodList = new ArrayList<>(foodList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = filteredFoodList.get(position);
        holder.bind(food, listener);
    }

    @Override
    public int getItemCount() {
        return filteredFoodList.size();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewFood;
        private final TextView textViewFoodName;
        private final TextView textViewQuantity;
        private final TextView textViewDonorName;


        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewFood = itemView.findViewById(R.id.imageViewFood);
            textViewFoodName = itemView.findViewById(R.id.foodNameTextView);
            textViewQuantity = itemView.findViewById(R.id.quantityTextView);
            textViewDonorName = itemView.findViewById(R.id.donorNameTextView);
        }

        @SuppressLint("SetTextI18n")
        public void bind(final Food food, final OnItemClickListener listener) {
            // Load food image using Picasso (replace with your image loading mechanism)
            Picasso.get().load(food.getImageUrl()).placeholder(R.drawable.placeholder_image).into(imageViewFood);

            // Set other details
            textViewFoodName.setText("Food: " + food.getFoodName());
            textViewQuantity.setText("Quantity: " + food.getQuantity());
            textViewDonorName.setText("Donor: " + food.getDonorName());

            // Handle item click
            itemView.setOnClickListener(view -> listener.onItemClick(food));
        }
    }
    public void filter(String query) {
        getFilter().filter(query);
    }
    public void filterList(List<Food> filteredList) {
        foodList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString().toLowerCase();

                List<Food> filteredList = new ArrayList<>();
                for (Food food : foodList) {
                    if (food.getFoodName().toLowerCase().contains(query)) {
                        filteredList.add(food);
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
                filteredFoodList.clear();
                if (filterResults.values instanceof List<?>) {
                    filteredFoodList.addAll((List<? extends Food>) filterResults.values);
                }
                notifyDataSetChanged();
            }
        };
    }
}
