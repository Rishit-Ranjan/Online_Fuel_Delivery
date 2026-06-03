package com.example.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.FuelStation;
import com.example.databinding.ItemAdminStationBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdminStationAdapter extends RecyclerView.Adapter<AdminStationAdapter.ViewHolder> {

    private List<FuelStation> stations = new ArrayList<>();
    private final FuelViewModel viewModel;

    public AdminStationAdapter(FuelViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setStations(List<FuelStation> stations) {
        this.stations = stations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminStationBinding binding = ItemAdminStationBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FuelStation station = stations.get(position);
        
        holder.binding.tvStationName.setText(station.getName());
        holder.binding.tvStationAddress.setText(station.getAddress());

        // Petrol
        holder.binding.tvPetrolStatus.setText(station.isPetrolAvailable() ? "IN STOCK" : "OUT OF STOCK");
        holder.binding.tvPetrolStatus.setTextColor(station.isPetrolAvailable() ? 0xFF2E7D32 : 0xFFC62828);
        
        holder.binding.switchPetrol.setOnCheckedChangeListener(null);
        holder.binding.switchPetrol.setChecked(station.isPetrolAvailable());
        holder.binding.switchPetrol.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.toggleFuelAvailability(station.getId(), "Petrol", isChecked);
        });

        holder.binding.tvPetrolPriceLabel.setText("Price: $" + String.format(Locale.US, "%.2f", station.getPetrolPrice()) + "/L");
        // Only set text if not focused to avoid interrupting user input
        if (!holder.binding.etPetrolPrice.hasFocus()) {
            holder.binding.etPetrolPrice.setText(String.format(Locale.US, "%.2f", station.getPetrolPrice()));
        }

        holder.binding.btnSavePetrol.setOnClickListener(v -> {
            String priceStr = holder.binding.etPetrolPrice.getText().toString();
            if (!priceStr.isEmpty()) {
                try {
                    double price = Double.parseDouble(priceStr);
                    viewModel.updateFuelPrice(station.getId(), "Petrol", price);
                    holder.binding.etPetrolPrice.clearFocus();
                } catch (NumberFormatException ignored) {}
            }
        });

        // Diesel
        holder.binding.tvDieselStatus.setText(station.isDieselAvailable() ? "IN STOCK" : "OUT OF STOCK");
        holder.binding.tvDieselStatus.setTextColor(station.isDieselAvailable() ? 0xFF2E7D32 : 0xFFC62828);
        
        holder.binding.switchDiesel.setOnCheckedChangeListener(null);
        holder.binding.switchDiesel.setChecked(station.isDieselAvailable());
        holder.binding.switchDiesel.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.toggleFuelAvailability(station.getId(), "Diesel", isChecked);
        });

        holder.binding.tvDieselPriceLabel.setText("Price: $" + String.format(Locale.US, "%.2f", station.getDieselPrice()) + "/L");
        if (!holder.binding.etDieselPrice.hasFocus()) {
            holder.binding.etDieselPrice.setText(String.format(Locale.US, "%.2f", station.getDieselPrice()));
        }

        holder.binding.btnSaveDiesel.setOnClickListener(v -> {
            String priceStr = holder.binding.etDieselPrice.getText().toString();
            if (!priceStr.isEmpty()) {
                try {
                    double price = Double.parseDouble(priceStr);
                    viewModel.updateFuelPrice(station.getId(), "Diesel", price);
                    holder.binding.etDieselPrice.clearFocus();
                } catch (NumberFormatException ignored) {}
            }
        });
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemAdminStationBinding binding;

        ViewHolder(ItemAdminStationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
