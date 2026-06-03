package com.example.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.R;
import com.example.data.FuelStation;
import com.example.databinding.DialogFuelOrderBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Locale;

public class OrderDialogFragment extends BottomSheetDialogFragment {

    private DialogFuelOrderBinding binding;
    private FuelViewModel viewModel;
    private FuelStation station;
    private String selectedFuelType = "Diesel";
    private String selectedPaymentMethod = "Cash on Delivery";

    public static OrderDialogFragment newInstance(FuelStation station) {
        OrderDialogFragment fragment = new OrderDialogFragment();
        fragment.station = station;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogFuelOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(FuelViewModel.class);

        setupUI();
        setupListeners();
        updateSummary();
    }

    @SuppressLint("SetTextI18n")
    private void setupUI() {
        if (station == null) return;

        binding.tvDialogStationName.setText("Station: " + station.getName());
        binding.tvPetrolLabel.setText("Petrol\n($" + String.format(Locale.US, "%.2f", station.getPetrolPrice()) + "/L)");
        binding.tvDieselLabel.setText("Diesel\n($" + String.format(Locale.US, "%.2f", station.getDieselPrice()) + "/L)");

        updateFuelTypeUI();
        updatePaymentUI();
    }

    private void setupListeners() {
        binding.btnSelectPetrol.setOnClickListener(v -> {
            selectedFuelType = "Petrol";
            updateFuelTypeUI();
            updateSummary();
        });

        binding.btnSelectDiesel.setOnClickListener(v -> {
            selectedFuelType = "Diesel";
            updateFuelTypeUI();
            updateSummary();
        });

        binding.etVolume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                updateSummary();
            }
        });

        binding.btn10l.setOnClickListener(v -> binding.etVolume.setText("10"));
        binding.btn20l.setOnClickListener(v -> binding.etVolume.setText("20"));
        binding.btn50l.setOnClickListener(v -> binding.etVolume.setText("50"));
        binding.btn100l.setOnClickListener(v -> binding.etVolume.setText("100"));

        binding.btnPayCash.setOnClickListener(v -> {
            selectedPaymentMethod = "Cash on Delivery";
            updatePaymentUI();
        });

        binding.btnPayWallet.setOnClickListener(v -> {
            selectedPaymentMethod = "Digital Wallet";
            updatePaymentUI();
        });

        binding.btnCancel.setOnClickListener(v -> dismiss());

        binding.btnConfirmOrder.setOnClickListener(v -> {
            String name = binding.etName.getText().toString();
            String phone = binding.etPhone.getText().toString();
            String address = binding.etAddress.getText().toString();
            String volumeStr = binding.etVolume.getText().toString();

            if (volumeStr.isEmpty()) {
                viewModel.postNotification("Please enter volume");
                return;
            }

            try {
                double volume = Double.parseDouble(volumeStr);
                viewModel.placeOrder(name, phone, address, station, selectedFuelType, volume, selectedPaymentMethod);
                dismiss();
            } catch (NumberFormatException e) {
                viewModel.postNotification("Invalid volume");
            }
        });
    }

    private void updateFuelTypeUI() {
        int activeColor = ContextCompat.getColor(requireContext(), R.color.diesel_active);
        int inactiveColor = ContextCompat.getColor(requireContext(), R.color.dark_sheet_surface);
        int white = ContextCompat.getColor(requireContext(), R.color.white);

        if ("Petrol".equals(selectedFuelType)) {
            binding.btnSelectPetrol.setCardBackgroundColor(activeColor);
            binding.tvPetrolLabel.setTypeface(null, android.graphics.Typeface.BOLD);

            binding.btnSelectDiesel.setCardBackgroundColor(inactiveColor);
            binding.tvDieselLabel.setTypeface(null, android.graphics.Typeface.NORMAL);
        } else {
            binding.btnSelectDiesel.setCardBackgroundColor(activeColor);
            binding.tvDieselLabel.setTypeface(null, android.graphics.Typeface.BOLD);

            binding.btnSelectPetrol.setCardBackgroundColor(inactiveColor);
            binding.tvPetrolLabel.setTypeface(null, android.graphics.Typeface.NORMAL);
        }
    }

    private void updatePaymentUI() {
        int activeBg = ContextCompat.getColor(requireContext(), R.color.white);
        int activeText = ContextCompat.getColor(requireContext(), R.color.dark_sheet_bg);
        int inactiveBg = ContextCompat.getColor(requireContext(), R.color.dark_sheet_surface);
        int inactiveText = ContextCompat.getColor(requireContext(), R.color.dark_on_surface_variant);

        if ("Cash on Delivery".equals(selectedPaymentMethod)) {
            binding.btnPayCash.setCardBackgroundColor(activeBg);
            binding.tvPayCashLabel.setTextColor(activeText);
            binding.tvPayCashLabel.setTypeface(null, android.graphics.Typeface.BOLD);
            binding.btnPayCash.setStrokeWidth(0);

            binding.btnPayWallet.setCardBackgroundColor(inactiveBg);
            binding.tvPayWalletLabel.setTextColor(inactiveText);
            binding.tvPayWalletLabel.setTypeface(null, android.graphics.Typeface.NORMAL);
            binding.btnPayWallet.setStrokeWidth(1);
            binding.btnPayWallet.setStrokeColor(activeBg);
        } else {
            binding.btnPayWallet.setCardBackgroundColor(activeBg);
            binding.tvPayWalletLabel.setTextColor(activeText);
            binding.tvPayWalletLabel.setTypeface(null, android.graphics.Typeface.BOLD);
            binding.btnPayWallet.setStrokeWidth(0);

            binding.btnPayCash.setCardBackgroundColor(inactiveBg);
            binding.tvPayCashLabel.setTextColor(inactiveText);
            binding.tvPayCashLabel.setTypeface(null, android.graphics.Typeface.NORMAL);
            binding.btnPayCash.setStrokeWidth(1);
            binding.btnPayCash.setStrokeColor(activeBg);
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateSummary() {
        if (station == null) return;

        double rate = "Petrol".equals(selectedFuelType) ? station.getPetrolPrice() : station.getDieselPrice();
        String volumeStr = binding.etVolume.getText().toString();
        double volumeVal = 0;
        if (!volumeStr.isEmpty()) {
            try {
                volumeVal = Double.parseDouble(volumeStr);
            } catch (NumberFormatException ignored) {}
        }

        double subtotal = volumeVal * rate;
        double deliveryFee = 0; // FREE as per screenshot

        binding.tvSummaryRate.setText("$" + String.format(Locale.US, "%.2f", rate) + "/Liter");
        binding.tvSummarySubtotal.setText("$" + String.format(Locale.US, "%.2f", subtotal));
        binding.tvSummaryTotal.setText("$" + String.format(Locale.US, "%.2f", subtotal + deliveryFee));
    }
}
