package com.egertj.fooddeliveryfeeapplication.weather;

import com.egertj.fooddeliveryfeeapplication.weather.model.WeatherData;
import com.egertj.fooddeliveryfeeapplication.weather.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;


@SpringBootTest()
@ActiveProfiles("test")
public class WeatherTests {

    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    public void testWeatherDataSaved(){
       WeatherData weatherData = new WeatherData();
       weatherData.setStationName("Tallinn");
       weatherData.setTimestamp(2000000);
       weatherData.setWmoCode(2000);
       weatherData.setWindSpeed(12);
       weatherData.setTemperature(10);

       weatherRepository.save(weatherData);

       List<WeatherData> data = weatherRepository.findAll();
       List<WeatherData> latest = weatherRepository.findLatestByStation("Tallinn");
       WeatherData tallinnData = latest.get(0);
       assertNotNull("Weather database is empty", data);
       assertEquals("Station name not equal", tallinnData.getStationName(), "Tallinn");
       assertEquals("WmoCode not equal", tallinnData.getWmoCode(), 2000);
       assertEquals("Wind speed not equal", tallinnData.getWindSpeed(), 12.0);
       weatherRepository.delete(weatherData);

    }




}
