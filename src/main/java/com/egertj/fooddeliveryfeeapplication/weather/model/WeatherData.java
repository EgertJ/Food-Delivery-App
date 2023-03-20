package com.egertj.fooddeliveryfeeapplication.weather.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String stationName;
    private int wmoCode;
    private double temperature;
    private double windSpeed;
    private String phenomenon;
    private long timestamp;

    public WeatherData(long id, String stationName, int wmoCode, double temperature, double windSpeed, String phenomenon, long timestamp) {
        this.id = id;
        this.stationName = stationName;
        this.wmoCode = wmoCode;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.phenomenon = phenomenon;
        this.timestamp = timestamp;
    }

    public WeatherData() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getWmoCode() {
        return wmoCode;
    }

    public void setWmoCode(int wmoCode) {
        this.wmoCode = wmoCode;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "id=" + id +
                ", stationName='" + stationName + '\'' +
                ", wmoCode=" + wmoCode +
                ", temperature=" + temperature +
                ", windSpeed=" + windSpeed +
                ", phenomenon='" + phenomenon + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherData that = (WeatherData) o;
        return id == that.id && wmoCode == that.wmoCode && Double.compare(that.temperature, temperature) == 0 && Double.compare(that.windSpeed, windSpeed) == 0 && timestamp == that.timestamp && Objects.equals(stationName, that.stationName) && Objects.equals(phenomenon, that.phenomenon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stationName, wmoCode, temperature, windSpeed, phenomenon, timestamp);
    }
}
