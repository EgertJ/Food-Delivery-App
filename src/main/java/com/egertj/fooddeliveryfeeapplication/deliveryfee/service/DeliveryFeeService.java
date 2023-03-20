package com.egertj.fooddeliveryfeeapplication.deliveryfee.service;

import com.egertj.fooddeliveryfeeapplication.businessrules.model.ExtraFee;
import com.egertj.fooddeliveryfeeapplication.businessrules.service.ExtraFeeService;
import com.egertj.fooddeliveryfeeapplication.businessrules.service.RBFService;
import com.egertj.fooddeliveryfeeapplication.deliveryfee.exception.DeliveryFeeException;
import com.egertj.fooddeliveryfeeapplication.deliveryfee.model.DeliveryFeeRequest;
import com.egertj.fooddeliveryfeeapplication.weather.model.WeatherData;
import com.egertj.fooddeliveryfeeapplication.weather.service.WeatherService;
import com.egertj.fooddeliveryfeeapplication.businessrules.model.RBF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryFeeService {

    @Autowired
    private RBFService rbfService;

    @Autowired
    private ExtraFeeService extraFeeService;
    @Autowired
    private WeatherService weatherService;

    private static final Logger logger = LoggerFactory.getLogger(DeliveryFeeService.class);

    private double RBF;
    private double ATEF;
    private double WSEF;
    private double WPEF;

    /**
     *
     * @param request REST API request
     * @return delivery fee calculated from the GET request
     * @throws DeliveryFeeException when there's something wrong with calculating the fee
     */
    public double calculateDeliveryFee(DeliveryFeeRequest request) throws DeliveryFeeException {

        String city = request.getCity();
        String weathercity;
        if (city.equals("Tartu")){
            weathercity = "Tartu-Tõravere";
        }
        else if (city.equals("Tallinn")){
            weathercity = "Tallinn-Harku";
        } else if (city.equals("Pärnu"))
            weathercity = "Pärnu";
        else throw new DeliveryFeeException("There doesn't exist data for city: " + city);

        if (!request.getVehicleType().equals("Bike") && !request.getVehicleType().equals("Car") && !request.getVehicleType().equals("Scooter")) {
            throw new DeliveryFeeException("You can't use this kind of transportation.");
        }



        WeatherData weatherData = weatherService.getWeatherDataByStation(weathercity).get(0);

        logger.info("Weather data for the requested city: " + weatherData);
        RBF = getRBF(request.getCity(), request.getVehicleType());
        logger.debug("RBF: " + RBF);

        ATEF = getATEF(weatherData.getTemperature(), request.getVehicleType());
        logger.debug("ATEF: " + ATEF);
        WPEF = getWPEF(weatherData.getPhenomenon(), request.getVehicleType());
        logger.debug("WPEF: " + WSEF);
        WSEF = getWSEF(weatherData.getWindSpeed(), request.getVehicleType());
        logger.debug("WSEF: " + WSEF);



        return RBF + ATEF + WPEF + WSEF;
    }

    private double getWPEF(String phenomenon, String vehicleType) throws DeliveryFeeException {
        List<ExtraFee> WPEFList = extraFeeService.getExtraFeeByConditionAndVehicleType("WPEF", vehicleType);
        for (ExtraFee extraFee : WPEFList) {
            if (extraFee.getPhenomenon() != null && phenomenon.toLowerCase().contains(extraFee.getPhenomenon().toLowerCase())) {
                if (extraFee.getFee() == null)
                    throw new DeliveryFeeException("Usage of selected vehicle type is forbidden");
                return extraFee.getFee();
            }
        }
        return 0;
    }

    private double getWSEF(double windSpeed, String vehicleType) throws DeliveryFeeException {
        List<ExtraFee> WSEFList = extraFeeService.getExtraFeeByConditionAndVehicleType("WSEF", vehicleType);
        for (ExtraFee extraFee: WSEFList){
            if (extraFee.getWind_speed_lower_bound() != null && extraFee.getTemperature_upper_bound() != null)
                if (windSpeed >= extraFee.getWind_speed_lower_bound() && windSpeed <= extraFee.getWind_speed_upper_bound()) return extraFee.getFee();

            if (extraFee.getWind_speed_lower_bound() != null && extraFee.getWind_speed_upper_bound() == null)
                if (windSpeed > extraFee.getWind_speed_lower_bound())
                    if (extraFee.getFee() == null)
                        throw new DeliveryFeeException("Usage of selected vehicle type is forbidden");
                    else return extraFee.getFee();

            if (extraFee.getWind_speed_lower_bound() == null && extraFee.getWind_speed_upper_bound() != null)
                if (windSpeed < extraFee.getWind_speed_upper_bound()) return extraFee.getFee();
        }
        return 0;
    }

    private double getATEF(double temperature, String vehicleType) {
        List<ExtraFee> ATEFList = extraFeeService.getExtraFeeByConditionAndVehicleType("ATEF", vehicleType);
        for (ExtraFee extraFee : ATEFList){

            if (extraFee.getTemperature_upper_bound() != null && extraFee.getTemperature_lower_bound() == null)
                if (temperature < extraFee.getTemperature_upper_bound())
                    return extraFee.getFee();

            if (extraFee.getTemperature_upper_bound() != null && extraFee.getTemperature_lower_bound() != null)
                if (temperature >= extraFee.getTemperature_lower_bound()&& temperature <= extraFee.getTemperature_upper_bound())
                    return extraFee.getFee();

            if (extraFee.getTemperature_upper_bound() == null && extraFee.getTemperature_lower_bound() != null)
                if (temperature > extraFee.getTemperature_lower_bound())
                    return extraFee.getFee();
        }
        return 0;
    }

    private double getRBF(String city, String vehicleType) throws DeliveryFeeException {

        RBF rbf = rbfService.getRBFByCityAndVehicleType(city,vehicleType);
        if (rbf == null)
            throw new DeliveryFeeException("Please enter a valid city and vehicle type");
        return rbf.getFee();
    }

}
