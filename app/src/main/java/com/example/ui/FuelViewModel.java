package com.example.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.AppDatabase;
import com.example.data.FuelOrder;
import com.example.data.FuelRepository;
import com.example.data.FuelStation;

import java.util.List;

public class FuelViewModel extends AndroidViewModel {

    private final FuelRepository repository;

    private final MutableLiveData<String> activeRole = new MutableLiveData<>("User");
    private final MutableLiveData<String> notification = new MutableLiveData<>();
    private final MutableLiveData<String> userScreenTab = new MutableLiveData<>("CompareStations");

    private final LiveData<List<FuelStation>> stations;
    private final LiveData<List<FuelOrder>> orders;

    public FuelViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getDatabase(application);
        repository = new FuelRepository(database);

        stations = repository.getAllStations();
        orders = repository.getAllOrders();

        repository.seedStationsIfEmpty();
    }

    public LiveData<String> getActiveRole() { return activeRole; }
    public LiveData<String> getNotification() { return notification; }
    public LiveData<String> getUserScreenTab() { return userScreenTab; }
    public LiveData<List<FuelStation>> getStations() { return stations; }
    public LiveData<List<FuelOrder>> getOrders() { return orders; }

    public void selectRole(String role) {
        activeRole.setValue(role);
        notification.setValue("Switched to " + role + " Workspace");
    }

    public void setUserScreenTab(String tab) {
        userScreenTab.setValue(tab);
    }

    public void postNotification(String message) {
        notification.setValue(message);
    }

    public void placeOrder(String customerName, String customerPhone, String deliveryAddress, FuelStation station, String fuelType, double quantityLiters, String paymentMethod) {
        if (customerName.isEmpty() || customerPhone.isEmpty() || deliveryAddress.isEmpty() || quantityLiters <= 0) {
            notification.setValue("Please fill in all details correctly.");
            return;
        }

        double pricePerLiter = fuelType.equals("Petrol") ? station.getPetrolPrice() : station.getDieselPrice();
        double totalAmount = quantityLiters * pricePerLiter;

        FuelOrder newOrder = FuelOrder.createDefault(customerName, customerPhone, deliveryAddress, station.getId(), station.getName(), fuelType, quantityLiters, pricePerLiter, totalAmount);
        newOrder.setPaymentMethod(paymentMethod);

        repository.insertOrder(newOrder, result -> {
            notification.postValue("Fuel Order Submitted! TXN: " + newOrder.getTransactionId());
            userScreenTab.postValue("OrderHistory");
        });
    }

    public void updateOrderStatus(int orderId, String newStatus, String modifierRole) {
        List<FuelOrder> currentOrders = orders.getValue();
        if (currentOrders != null) {
            for (FuelOrder order : currentOrders) {
                if (order.getId() == orderId) {
                    order.setStatus(newStatus);
                    repository.updateOrder(order);
                    notification.setValue("Order #" + orderId + " is now: " + newStatus + " (" + modifierRole + ")");
                    break;
                }
            }
        }
    }

    public void cancelOrder(int orderId, String cancelledBy) {
        List<FuelOrder> currentOrders = orders.getValue();
        if (currentOrders != null) {
            for (FuelOrder order : currentOrders) {
                if (order.getId() == orderId) {
                    if (order.getStatus().equals("Delivered") || order.getStatus().equals("Cancelled")) {
                        notification.setValue("Cannot cancel order which is already " + order.getStatus().toLowerCase() + ".");
                        return;
                    }
                    order.setStatus("Cancelled");
                    repository.updateOrder(order);
                    notification.setValue("Order #" + orderId + " has been Cancelled by " + cancelledBy);
                    break;
                }
            }
        }
    }

    public void modifyOrder(int orderId, double newQuantity, String newFuelType, String modifiedBy) {
        List<FuelOrder> currentOrders = orders.getValue();
        if (currentOrders != null) {
            for (FuelOrder order : currentOrders) {
                if (order.getId() == orderId) {
                    if (!order.getStatus().equals("Pending") && !order.getStatus().equals("Confirmed")) {
                        notification.setValue("Order already in progress (" + order.getStatus() + "). Cannot modify now.");
                        return;
                    }

                    FuelStation targetStation = null;
                    List<FuelStation> currentStations = stations.getValue();
                    if (currentStations != null) {
                        for (FuelStation s : currentStations) {
                            if (s.getId() == order.getStationId()) {
                                targetStation = s;
                                break;
                            }
                        }
                    }

                    if (targetStation == null) {
                        notification.setValue("Error fetching station details.");
                        return;
                    }

                    double targetPrice = newFuelType.equals("Petrol") ? targetStation.getPetrolPrice() : targetStation.getDieselPrice();
                    double totalAmount = newQuantity * targetPrice;

                    order.setFuelType(newFuelType);
                    order.setQuantityLiters(newQuantity);
                    order.setPricePerLiter(targetPrice);
                    order.setTotalAmount(totalAmount);
                    order.setTransactionId(order.getTransactionId() + "-M");

                    repository.updateOrder(order);
                    notification.setValue("Order #" + orderId + " modified by " + modifiedBy + ". Standard charge recalculated.");
                    break;
                }
            }
        }
    }

    public void assignDriver(int orderId, String driverName, String driverPhone) {
        List<FuelOrder> currentOrders = orders.getValue();
        if (currentOrders != null) {
            for (FuelOrder order : currentOrders) {
                if (order.getId() == orderId) {
                    order.setDriverName(driverName);
                    order.setDriverPhone(driverPhone);
                    order.setStatus("Confirmed");
                    repository.updateOrder(order);
                    notification.setValue("Assigned driver " + driverName + " to Order #" + orderId);
                    break;
                }
            }
        }
    }

    public void toggleFuelAvailability(int stationId, String fuelType, boolean isAvailable) {
        List<FuelStation> currentStations = stations.getValue();
        if (currentStations != null) {
            for (FuelStation station : currentStations) {
                if (station.getId() == stationId) {
                    if (fuelType.equals("Petrol")) {
                        station.setPetrolAvailable(isAvailable);
                    } else {
                        station.setDieselAvailable(isAvailable);
                    }
                    repository.updateStation(station);
                    notification.setValue(station.getName() + " updated: " + fuelType + " is now " + (isAvailable ? "Available" : "Unavailable"));
                    break;
                }
            }
        }
    }

    public void updateFuelPrice(int stationId, String fuelType, double newPrice) {
        if (newPrice <= 0) return;
        List<FuelStation> currentStations = stations.getValue();
        if (currentStations != null) {
            for (FuelStation station : currentStations) {
                if (station.getId() == stationId) {
                    if (fuelType.equals("Petrol")) {
                        station.setPetrolPrice(newPrice);
                    } else {
                        station.setDieselPrice(newPrice);
                    }
                    repository.updateStation(station);
                    notification.setValue(station.getName() + " updated: " + fuelType + " price changed to $" + newPrice + "/L");
                    break;
                }
            }
        }
    }
}
