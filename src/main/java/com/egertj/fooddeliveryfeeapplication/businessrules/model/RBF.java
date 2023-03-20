package com.egertj.fooddeliveryfeeapplication.businessrules.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;
@Entity
public class RBF {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String city;
    private String vehicleType;
    private double fee;

    public RBF() {
    }

    public RBF(long id, String city, String vehicleType, double fee) {
        this.id = id;
        this.city = city;
        this.vehicleType = vehicleType;
        this.fee = fee;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "RBF{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", fee=" + fee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RBF rbf = (RBF) o;
        return id == rbf.id && Double.compare(rbf.fee, fee) == 0 && Objects.equals(city, rbf.city) && Objects.equals(vehicleType, rbf.vehicleType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, vehicleType, fee);
    }
}
