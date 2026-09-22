package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private List<PantryItem> pantryItems;
    private OnPantryItemActionListener listener;

    // Interface for Edit and Delete actions
    public interface OnPantryItemActionListener {
        void onEdit(PantryItem item);
        void onDelete(PantryItem item);
    }

    // Constructor
    public PantryAdapter(
            List<PantryItem> pantryItems,
            OnPantryItemActionListener listener
    ) {
        this.pantryItems = pantryItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PantryViewHolder holder,
            int position
    ) {

        PantryItem item = pantryItems.get(position);

        // Display ingredient name
        holder.tvItemName.setText(item.getName());

        // Display quantity and unit
        holder.tvItemQuantity.setText(
                item.getQuantity() + " " + item.getUnit()
        );

        // Display expiry date
        if (item.getExpiryDate() == null ||
                item.getExpiryDate().isEmpty()) {

            holder.tvItemExpiry.setText("No expiry date");

        } else {

            holder.tvItemExpiry.setText(
                    "Expires: " + item.getExpiryDate()
            );
        }

        // Edit button
        holder.btnEditItem.setOnClickListener(v -> {

            if (listener != null) {
                listener.onEdit(item);
            }
        });

        // Delete button
        holder.btnDeleteItem.setOnClickListener(v -> {

            if (listener != null) {
                listener.onDelete(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public static class PantryViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvItemName;
        TextView tvItemQuantity;
        TextView tvItemExpiry;

        Button btnEditItem;
        Button btnDeleteItem;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemName = itemView.findViewById(
                    R.id.tvItemName
            );

            tvItemQuantity = itemView.findViewById(
                    R.id.tvItemQuantity
            );

            tvItemExpiry = itemView.findViewById(
                    R.id.tvItemExpiry
            );

            btnEditItem = itemView.findViewById(
                    R.id.btnEditItem
            );

            btnDeleteItem = itemView.findViewById(
                    R.id.btnDeleteItem
            );
        }
    }
}
