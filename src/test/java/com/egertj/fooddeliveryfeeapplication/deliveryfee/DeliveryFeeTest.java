package com.egertj.fooddeliveryfeeapplication.deliveryfee;

import com.egertj.fooddeliveryfeeapplication.businessrules.exception.BusinessRuleException;
import com.egertj.fooddeliveryfeeapplication.businessrules.model.ExtraFee;
import com.egertj.fooddeliveryfeeapplication.businessrules.model.RBF;
import com.egertj.fooddeliveryfeeapplication.businessrules.service.ExtraFeeService;
import com.egertj.fooddeliveryfeeapplication.businessrules.service.RBFService;
import com.egertj.fooddeliveryfeeapplication.deliveryfee.exception.DeliveryFeeException;
import com.egertj.fooddeliveryfeeapplication.deliveryfee.model.DeliveryFeeRequest;
import com.egertj.fooddeliveryfeeapplication.deliveryfee.service.DeliveryFeeService;
import com.egertj.fooddeliveryfeeapplication.weather.model.WeatherData;
import com.egertj.fooddeliveryfeeapplication.weather.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class DeliveryFeeTest {

    @Autowired
    private ExtraFeeService extraFeeService;
    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private DeliveryFeeService deliveryFeeService;

    @Autowired
    private RBFService rbfService;

    @Test
    public void testDeliveryFee() throws BusinessRuleException, DeliveryFeeException {

        WeatherData weatherData = new WeatherData();
        weatherData.setStationName("Tallinn-Harku");
        weatherData.setWmoCode(2000);
        weatherData.setTimestamp(22222223333L);
        weatherData.setWindSpeed(25);
        weatherData.setTemperature(-4);
        weatherData.setPhenomenon("Thunder");

        weatherRepository.save(weatherData);

        WeatherData weatherData2 = new WeatherData();
        weatherData2.setStationName("Tartu-Tõravere");
        weatherData2.setWmoCode(2000);
        weatherData2.setTimestamp(22222223333L);
        weatherData2.setWindSpeed(4.7);
        weatherData2.setTemperature(-2.1);
        weatherData2.setPhenomenon("Light snow shower");

        weatherRepository.save(weatherData2);

        RBF rbf = new RBF();
        rbf.setId(1);
        rbf.setCity("Tallinn");
        rbf.setVehicleType("Bike");
        rbf.setFee(10);

        RBF rbf2 = new RBF();
        rbf2.setId(2);
        rbf2.setCity("Tartu");
        rbf2.setVehicleType("Bike");
        rbf2.setFee(2.5);

        rbfService.addRBFData(rbf);
        rbfService.addRBFData(rbf2);


        ExtraFee extraFee = new ExtraFee(1, "WSEF", "Bike", null,
                null, 20.0, null, null, null);

        ExtraFee extraFee2 = new ExtraFee(2, "ATEF", "Bike", -10.0,
                0.0, null, null, null, 0.5);

        ExtraFee extraFee3 = new ExtraFee(3, "WPEF", "Bike", null,
                null, null, null, "snow", 1.0);

        extraFeeService.addExtraFeeData(extraFee);
        extraFeeService.addExtraFeeData(extraFee2);
        extraFeeService.addExtraFeeData(extraFee3);

        try {
            double fee = deliveryFeeService.calculateDeliveryFee(new DeliveryFeeRequest("Tallinn", "Bike"));
        }catch (DeliveryFeeException e){
            System.out.println(rbfService.getAllRBFData());
            assertEquals("Error with Tallinn and Bike", "Usage of selected vehicle type is forbidden", e.getMessage());
        }

        double fee2 = deliveryFeeService.calculateDeliveryFee(new DeliveryFeeRequest("Tartu", "Bike"));

        assertEquals("Error with Tartu and Bike", 4.0, fee2);

        weatherRepository.delete(weatherData);
        weatherRepository.delete(weatherData2);
        extraFeeService.deleteExtraFee(1L);
        extraFeeService.deleteExtraFee(2L);
        extraFeeService.deleteExtraFee(3L);
        rbfService.deleteRBF(1L);
        rbfService.deleteRBF(2L);

    }
}
