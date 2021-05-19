package com.example.taxirentingapp;

import android.location.Location;

import java.util.List;

public class User extends Account {
    private long balance;
    private int bookings;
    private List<Driver> driversList;

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public int getBookings() {
        return bookings;
    }

    public void setBookings(int bookings) {
        this.bookings = bookings;
    }

    public List<Driver> getDriversList() {
        return driversList;
    }

    public void setDriversList(List<Driver> driversList) {
        this.driversList = driversList;
    }

    @Override
    public Location getDestinationLocation() {
        return super.getDestinationLocation();
    }

    @Override
    public void setDestinationLocation(Location destinationLocation) {
        super.setDestinationLocation(destinationLocation);
    }

    @Override
    public Location getDriverLocation() {
        return super.getDriverLocation();
    }

    @Override
    public void setDriverLocation(Location driverLocation) {
        super.setDriverLocation(driverLocation);
    }

    @Override
    public Location getUserLocation() {
        return super.getUserLocation();
    }

    @Override
    public void setUserLocation(Location userLocation) {
        super.setUserLocation(userLocation);
    }

    @Override
    public String getFullName() {
        return super.getFullName();
    }

    @Override
    public void setFullName(String fullName) {
        super.setFullName(fullName);
    }

    @Override
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        super.setPhoneNumber(phoneNumber);
    }

    @Override
    public int getUserId() {
        return super.getUserId();
    }

    @Override
    public void setUserId(int userId) {
        super.setUserId(userId);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }
}
