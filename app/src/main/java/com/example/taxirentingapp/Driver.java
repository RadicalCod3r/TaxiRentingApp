package com.example.taxirentingapp;

import android.graphics.drawable.Drawable;
import android.location.Location;

import java.util.List;

public class Driver extends Account {
    private String carModel;
    private String age;
    private Drawable carImage;
    private float averageStars;
    private int peopleVoted;
    private int income;
    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Drawable getCarImage() {
        return carImage;
    }

    public void setCarImage(Drawable carImage) {
        this.carImage = carImage;
    }

    public float getAverageStars() {
        return averageStars;
    }

    public void setAverageStars(float averageStars) {
        this.averageStars = averageStars;
    }

    public int getPeopleVoted() {
        return peopleVoted;
    }

    public void setPeopleVoted(int peopleVoted) {
        this.peopleVoted = peopleVoted;
    }

    public long getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    @Override
    public boolean isDriver() {
        return super.isDriver();
    }

    @Override
    public void setDriver(boolean driver) {
        super.setDriver(driver);
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
