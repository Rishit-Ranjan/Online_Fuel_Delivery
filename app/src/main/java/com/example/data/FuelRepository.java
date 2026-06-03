package com.example.data;

import androidx.lifecycle.LiveData;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FuelRepository {
    private final FuelStationDao fuelStationDao;
    private final OrderDao orderDao;
    private final ExecutorService executorService;

    public FuelRepository(AppDatabase database) {
        this.fuelStationDao = database.fuelStationDao();
        this.orderDao = database.orderDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<FuelStation>> getAllStations() {
        return fuelStationDao.getAllStations();
    }

    public LiveData<List<FuelOrder>> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public LiveData<FuelStation> getStationById(int id) {
        return fuelStationDao.getStationById(id);
    }

    public LiveData<FuelOrder> getOrderById(int id) {
        return orderDao.getOrderById(id);
    }

    public void insertOrder(FuelOrder order, RepositoryCallback<Long> callback) {
        executorService.execute(() -> {
            long id = orderDao.insertOrder(order);
            if (callback != null) callback.onComplete(id);
        });
    }

    public void updateOrder(FuelOrder order) {
        executorService.execute(() -> orderDao.updateOrder(order));
    }

    public void updateStation(FuelStation station) {
        executorService.execute(() -> fuelStationDao.updateStation(station));
    }

    public void seedStationsIfEmpty() {
        executorService.execute(() -> {
            if (fuelStationDao.getStationCount() == 0) {
                List<FuelStation> dummyStations = new ArrayList<>();
                dummyStations.add(new FuelStation("Shell Premium Station", "120 Expressway Road, Sector 4", 4.8, 1.2, 1.48, 1.32, true, true));
                dummyStations.add(new FuelStation("Chevron QuickFill", "45 Metro Center Boulevard", 4.5, 2.4, 1.45, 1.30, true, true));
                dummyStations.add(new FuelStation("ExxonMobil Express", "89 Outer Ring Circle Highway", 4.2, 3.8, 1.42, 1.28, true, false));
                dummyStations.add(new FuelStation("BP Green Energy Hub", "21 Eco Sanctuary Drive", 4.7, 0.5, 1.52, 1.36, false, true));
                dummyStations.add(new FuelStation("Texaco FastStop", "312 Countryside Access Rd", 3.9, 5.6, 1.39, 1.25, true, true));
                fuelStationDao.insertStations(dummyStations);
            }
        });
    }

    public interface RepositoryCallback<T> {
        void onComplete(T result);
    }
}
