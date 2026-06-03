package com.example;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.databinding.ActivityMainBinding;
import com.example.ui.AdminFragment;
import com.example.ui.DriverFragment;
import com.example.ui.FuelViewModel;
import com.example.ui.FuelViewModelFactory;
import com.example.ui.UserFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FuelViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        androidx.core.view.WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, windowInsets) -> {
            androidx.core.graphics.Insets insets = windowInsets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars());
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return windowInsets;
        });

        viewModel = new ViewModelProvider(this, new FuelViewModelFactory(getApplication()))
                .get(FuelViewModel.class);

        setupRoleSwitcher();
        observeViewModel();
    }

    private void setupRoleSwitcher() {
        binding.btnUserRole.setOnClickListener(v -> viewModel.selectRole("User"));
        binding.btnAdminRole.setOnClickListener(v -> viewModel.selectRole("Admin"));
        binding.btnDriverRole.setOnClickListener(v -> viewModel.selectRole("Driver"));
    }

    private void observeViewModel() {
        viewModel.getActiveRole().observe(this, role -> {
            // Update UI based on active role
            updateWorkspace(role);
            updateRoleButtons(role);
        });
    }

    private void updateWorkspace(String role) {
        Fragment fragment;
        switch (role) {
            case "Admin":
                fragment = new AdminFragment();
                break;
            case "Driver":
                fragment = new DriverFragment();
                break;
            default:
                fragment = new UserFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    private void updateRoleButtons(String activeRole) {
        // Change button colors and indicator to show active role
        updateRoleState(binding.btnUserRole, activeRole.equals("User"));
        updateRoleState(binding.btnAdminRole, activeRole.equals("Admin"));
        updateRoleState(binding.btnDriverRole, activeRole.equals("Driver"));
    }

    private void updateRoleState(View view, boolean isActive) {
        view.setBackgroundResource(isActive ? R.drawable.active_role_bg : 0);
        
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup group = (android.view.ViewGroup) view;
            int textColor = isActive ? 0xFF001D35 : 0xFF49454F; // geo_on_primary_container vs geo_on_surface_variant
            int iconColor = isActive ? 0xFF001D35 : 0xFF49454F;

            for (int i = 0; i < group.getChildCount(); i++) {
                View child = group.getChildAt(i);
                if (child instanceof android.widget.TextView) {
                    ((android.widget.TextView) child).setTextColor(textColor);
                } else if (child instanceof android.widget.ImageView) {
                    ((android.widget.ImageView) child).setColorFilter(iconColor);
                }
            }
        }
    }
}
