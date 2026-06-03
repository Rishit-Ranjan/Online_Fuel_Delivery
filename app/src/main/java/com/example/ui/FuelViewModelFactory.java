package com.example.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FuelViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public FuelViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FuelViewModel.class)) {
            return (T) new FuelViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
