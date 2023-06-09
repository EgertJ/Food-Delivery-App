package com.egertj.fooddeliveryfeeapplication.deliveryfee.model;

public class DeliveryFeeRequest {

    private String city;
    private String vehicleType;

    public DeliveryFeeRequest(String city, String vehicleType) {
        this.city = city;
        this.vehicleType = vehicleType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
