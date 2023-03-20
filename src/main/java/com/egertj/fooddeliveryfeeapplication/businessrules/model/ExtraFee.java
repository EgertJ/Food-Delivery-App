package com.egertj.fooddeliveryfeeapplication.businessrules.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class ExtraFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String condition;
    private String vehicleType;

    private Double temperature_lower_bound;

    private Double temperature_upper_bound;

    private Double wind_speed_lower_bound;

    private Double wind_speed_upper_bound;

    private String phenomenon;
    private Double fee;

    public ExtraFee() {
    }

    public ExtraFee(long id, String condition, String vehicleType, Double temperature_lower_bound,
                    Double temperature_upper_bound, Double wind_speed_lower_bound, Double wind_speed_upper_bound, String phenomenon, Double fee) {
        this.id = id;
        this.condition = condition;
        this.vehicleType = vehicleType;
        this.temperature_lower_bound = temperature_lower_bound;
        this.temperature_upper_bound = temperature_upper_bound;
        this.wind_speed_lower_bound = wind_speed_lower_bound;
        this.wind_speed_upper_bound = wind_speed_upper_bound;
        this.phenomenon = phenomenon;
        this.fee = fee;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Double getTemperature_lower_bound() {
        return temperature_lower_bound;
    }

    public void setTemperature_lower_bound(Double temperature_lower_bound) {
        this.temperature_lower_bound = temperature_lower_bound;
    }

    public Double getTemperature_upper_bound() {
        return temperature_upper_bound;
    }

    public void setTemperature_upper_bound(Double temperature_upper_bound) {
        this.temperature_upper_bound = temperature_upper_bound;
    }

    public Double getWind_speed_lower_bound() {
        return wind_speed_lower_bound;
    }

    public void setWind_speed_lower_bound(Double wind_speed_lower_bound) {
        this.wind_speed_lower_bound = wind_speed_lower_bound;
    }

    public Double getWind_speed_upper_bound() {
        return wind_speed_upper_bound;
    }

    public void setWind_speed_upper_bound(Double wind_speed_upper_bound) {
        this.wind_speed_upper_bound = wind_speed_upper_bound;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "ExtraFee{" +
                "id=" + id +
                ", condition='" + condition + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", temperature_lower_bound=" + temperature_lower_bound +
                ", temperature_upper_bound=" + temperature_upper_bound +
                ", wind_speed_lower_bound=" + wind_speed_lower_bound +
                ", wind_speed_upper_bound=" + wind_speed_upper_bound +
                ", phenomenon='" + phenomenon + '\'' +
                ", fee=" + fee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtraFee extraFee = (ExtraFee) o;
        return id == extraFee.id && Objects.equals(condition, extraFee.condition) && Objects.equals(vehicleType, extraFee.vehicleType) && Objects.equals(temperature_lower_bound, extraFee.temperature_lower_bound) && Objects.equals(temperature_upper_bound, extraFee.temperature_upper_bound) && Objects.equals(wind_speed_lower_bound, extraFee.wind_speed_lower_bound) && Objects.equals(wind_speed_upper_bound, extraFee.wind_speed_upper_bound) && Objects.equals(phenomenon, extraFee.phenomenon) && Objects.equals(fee, extraFee.fee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, condition, vehicleType, temperature_lower_bound, temperature_upper_bound, wind_speed_lower_bound, wind_speed_upper_bound, phenomenon, fee);
    }
}

