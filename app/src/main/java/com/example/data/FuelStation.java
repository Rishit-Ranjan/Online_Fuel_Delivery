package com.example.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fuel_stations")
public class FuelStation {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String name;
    private String address;
    private double rating;
    private double distance; // in km
    private double petrolPrice; // price per liter
    private double dieselPrice; // price per liter
    private boolean isPetrolAvailable;
    private boolean isDieselAvailable;

    public FuelStation(String name, String address, double rating, double distance, double petrolPrice, double dieselPrice, boolean isPetrolAvailable, boolean isDieselAvailable) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.distance = distance;
        this.petrolPrice = petrolPrice;
        this.dieselPrice = dieselPrice;
        this.isPetrolAvailable = isPetrolAvailable;
        this.isDieselAvailable = isDieselAvailable;
    }

    // Getters and Seters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }

    public String getAddress() { return address; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
    public double getPetrolPrice() { return petrolPrice; }
    public void setPetrolPrice(double petrolPrice) { this.petrolPrice = petrolPrice; }
    public double getDieselPrice() { return dieselPrice; }
    public void setDieselPrice(double dieselPrice) { this.dieselPrice = dieselPrice; }
    public boolean isPetrolAvailable() { return isPetrolAvailable; }
    public void setPetrolAvailable(boolean petrolAvailable) { isPetrolAvailable = petrolAvailable; }
    public boolean isDieselAvailable() { return isDieselAvailable; }
    public void setDieselAvailable(boolean dieselAvailable) { isDieselAvailable = dieselAvailable; }
}
