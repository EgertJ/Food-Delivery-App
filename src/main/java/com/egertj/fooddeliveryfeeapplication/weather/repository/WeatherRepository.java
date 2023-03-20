package com.egertj.fooddeliveryfeeapplication.weather.repository;

import com.egertj.fooddeliveryfeeapplication.weather.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherData, Long> {
    //Finds weather data by station name, ordered by timestamp
    @Query("SELECT wd from WeatherData wd WHERE wd.stationName = :stationName order by wd.timestamp DESC")
    List<WeatherData> findLatestByStation(@Param("stationName") String stationName);
}
