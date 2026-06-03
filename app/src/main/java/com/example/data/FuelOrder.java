package com.example.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Random;

@Entity(tableName = "fuel_orders")
public class FuelOrder {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String customerName;
    private String customerPhone;
    private String deliveryAddress;
    private int stationId;
    private String stationName;
    private String fuelType; // "Petrol" or "Diesel"
    private double quantityLiters;
    private double pricePerLiter;
    private double totalAmount;
    private String status; // "Pending", "Confirmed", "In Transit", "Delivered", "Cancelled"
    private long timestamp;
    private String paymentMethod;
    private String driverName;
    private String driverPhone;
    private String transactionId;

    public FuelOrder(String customerName, String customerPhone, String deliveryAddress, int stationId, String stationName, String fuelType, double quantityLiters, double pricePerLiter, double totalAmount, String status, long timestamp, String paymentMethod, String driverName, String driverPhone, String transactionId) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.deliveryAddress = deliveryAddress;
        this.stationId = stationId;
        this.stationName = stationName;
        this.fuelType = fuelType;
        this.quantityLiters = quantityLiters;
        this.pricePerLiter = pricePerLiter;
        this.totalAmount = totalAmount;
        this.status = status;
        this.timestamp = timestamp;
        this.paymentMethod = paymentMethod;
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        this.transactionId = transactionId;
    }

    public static FuelOrder createDefault(String customerName, String customerPhone, String deliveryAddress, int stationId, String stationName, String fuelType, double quantityLiters, double pricePerLiter, double totalAmount) {
        return new FuelOrder(
                customerName,
                customerPhone,
                deliveryAddress,
                stationId,
                stationName,
                fuelType,
                quantityLiters,
                pricePerLiter,
                totalAmount,
                "Pending",
                System.currentTimeMillis(),
                "Cash on Delivery",
                "",
                "",
                "TXN" + (100000 + new Random().nextInt(900000))
        );
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public int getStationId() { return stationId; }
    public void setStationId(int stationId) { this.stationId = stationId; }
    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }
    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public double getQuantityLiters() { return quantityLiters; }
    public void setQuantityLiters(double quantityLiters) { this.quantityLiters = quantityLiters; }
    public double getPricePerLiter() { return pricePerLiter; }
    public void setPricePerLiter(double pricePerLiter) { this.pricePerLiter = pricePerLiter; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    public String getDriverPhone() { return driverPhone; }
    public void setDriverPhone(String driverPhone) { this.driverPhone = driverPhone; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
}
