package com.example.ui;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.FuelOrder;
import com.example.databinding.ItemOrderCardBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<FuelOrder> orders = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setOrders(List<FuelOrder> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderCardBinding binding = ItemOrderCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FuelOrder order = orders.get(position);
        
        holder.binding.tvOrderId.setText("TXN #" + order.getTransactionId());
        holder.binding.tvStationName.setText(order.getStationName());
        holder.binding.tvOrderDetails.setText(
                String.format(Locale.US, "%.0fL %s • $%.2f", 
                order.getQuantityLiters(), order.getFuelType(), order.getTotalAmount()));
        holder.binding.tvOrderStatus.setText(order.getStatus());
        holder.binding.tvDeliveryAddress.setText(order.getDeliveryAddress());
        
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                order.getTimestamp(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
        holder.binding.tvOrderTime.setText(timeAgo);

        // Simple status coloring
        updateStatusUI(holder, order.getStatus());
    }

    private void updateStatusUI(ViewHolder holder, String status) {
        int color;
        int bgColor;
        
        switch (status) {
            case "Delivered":
                color = 0xFF2E7D32; // Green
                bgColor = 0xFFE8F5E9;
                break;
            case "Cancelled":
                color = 0xFFC62828; // Red
                bgColor = 0xFFFFEBEE;
                break;
            case "In Transit":
                color = 0xFFF57C00; // Orange
                bgColor = 0xFFFFF3E0;
                break;
            default: // Pending, Confirmed
                color = 0xFF1976D2; // Blue
                bgColor = 0xFFE3F2FD;
                break;
        }
        
        holder.binding.tvOrderStatus.setTextColor(color);
        holder.binding.tvOrderStatus.setAlpha(1.0f);
        ((androidx.cardview.widget.CardView) holder.binding.tvOrderStatus.getParent()).setCardBackgroundColor(bgColor);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemOrderCardBinding binding;

        ViewHolder(ItemOrderCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
