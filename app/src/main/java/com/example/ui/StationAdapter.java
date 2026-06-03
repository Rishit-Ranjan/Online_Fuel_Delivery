package com.example.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.FuelStation;
import com.example.databinding.ItemStationCardBinding;

import java.util.ArrayList;
import java.util.List;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> {

    private List<FuelStation> stations = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setStations(List<FuelStation> stations) {
        this.stations = stations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStationCardBinding binding = ItemStationCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FuelStation station = stations.get(position);
        holder.binding.tvStationName.setText(station.getName());
        holder.binding.tvRatingDistance.setText(station.getRating() + "  •  " + station.getDistance() + " km");
        holder.binding.tvPetrolPrice.setText("$" + station.getPetrolPrice() + "/L");
        holder.binding.tvDieselPrice.setText("$" + station.getDieselPrice() + "/L");
        holder.binding.tvAvailability.setText(
                (station.isPetrolAvailable() || station.isDieselAvailable()) ? "Available" : "Low Stock");
        holder.binding.tvAvailability.setTextColor(
                (station.isPetrolAvailable() || station.isDieselAvailable()) ? 0xFF2E7D32 : 0xFFC62828);

        holder.binding.btnOrderGas.setOnClickListener(v -> {
            if (v.getContext() instanceof androidx.fragment.app.FragmentActivity) {
                androidx.fragment.app.FragmentActivity activity = (androidx.fragment.app.FragmentActivity) v.getContext();
                OrderDialogFragment.newInstance(station).show(activity.getSupportFragmentManager(), "OrderDialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemStationCardBinding binding;

        ViewHolder(ItemStationCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
