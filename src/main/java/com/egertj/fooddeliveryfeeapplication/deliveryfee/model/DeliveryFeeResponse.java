package com.egertj.fooddeliveryfeeapplication.deliveryfee.model;


public class DeliveryFeeResponse {
    private double fee;

    public DeliveryFeeResponse(double fee) {
        this.fee = fee;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
