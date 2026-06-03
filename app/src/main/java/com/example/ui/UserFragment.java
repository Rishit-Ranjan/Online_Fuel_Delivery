package com.example.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.databinding.FragmentUserWorkspaceBinding;

public class UserFragment extends Fragment {

    private FragmentUserWorkspaceBinding binding;
    private FuelViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserWorkspaceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(FuelViewModel.class);

        setupTabs();
        setupStationsList();
        setupOrdersList();
    }

    private void setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(new com.google.android.material.tabs.TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(com.google.android.material.tabs.TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    binding.stationsContainer.setVisibility(View.VISIBLE);
                    binding.ordersContainer.setVisibility(View.GONE);
                } else {
                    binding.stationsContainer.setVisibility(View.GONE);
                    binding.ordersContainer.setVisibility(View.VISIBLE);
                }
            }

            @Override public void onTabUnselected(com.google.android.material.tabs.TabLayout.Tab tab) {}
            @Override public void onTabReselected(com.google.android.material.tabs.TabLayout.Tab tab) {}
        });
    }

    private void setupStationsList() {
        StationAdapter adapter = new StationAdapter();
        binding.recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        viewModel.getStations().observe(getViewLifecycleOwner(), adapter::setStations);
    }

    private void setupOrdersList() {
        OrderAdapter adapter = new OrderAdapter();
        binding.recyclerViewOrders.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));
        binding.recyclerViewOrders.setAdapter(adapter);

        viewModel.getOrders().observe(getViewLifecycleOwner(), orders -> {
            boolean hasOrders = orders != null && !orders.isEmpty();
            binding.recyclerViewOrders.setVisibility(hasOrders ? View.VISIBLE : View.GONE);
            binding.emptyOrdersState.setVisibility(hasOrders ? View.GONE : View.VISIBLE);
            
            if (hasOrders) {
                adapter.setOrders(orders);
            }
        });
    }
}
