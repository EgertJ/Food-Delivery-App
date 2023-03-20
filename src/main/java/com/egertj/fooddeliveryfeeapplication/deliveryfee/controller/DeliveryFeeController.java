package com.egertj.fooddeliveryfeeapplication.deliveryfee.controller;

import com.egertj.fooddeliveryfeeapplication.deliveryfee.exception.DeliveryFeeException;
import com.egertj.fooddeliveryfeeapplication.deliveryfee.model.DeliveryFeeErrorResponse;
import com.egertj.fooddeliveryfeeapplication.deliveryfee.model.DeliveryFeeRequest;
import com.egertj.fooddeliveryfeeapplication.deliveryfee.model.DeliveryFeeResponse;
import com.egertj.fooddeliveryfeeapplication.deliveryfee.service.DeliveryFeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryFeeController {

    @Autowired
    private DeliveryFeeService deliveryFeeService;

    private static final Logger logger = LoggerFactory.getLogger(DeliveryFeeController.class);

    @GetMapping("/calculate-delivery-fee")
    public ResponseEntity<?> calculateFee(@RequestParam String city, @RequestParam String vehicleType) throws DeliveryFeeException {
        try {
            logger.info("New request arrived with parameters: " + "City: "+ city + " Vehicle type: " + vehicleType);
            DeliveryFeeRequest request = new DeliveryFeeRequest(city, vehicleType);
            double fee =deliveryFeeService.calculateDeliveryFee(request);
            logger.debug("Calculated fee: " + fee);
            DeliveryFeeResponse response = new DeliveryFeeResponse(fee);

            logger.info("Returned response: ok");

            return ResponseEntity.ok(response);
        } catch (DeliveryFeeException e){
            DeliveryFeeErrorResponse error = new DeliveryFeeErrorResponse();
            logger.error("Error with the delivery fee calculation.");
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            error.setMessage("Failed to calculate fee");
            error.setError(e.getMessage());

            logger.info("Returned response: badRequest");
            return ResponseEntity.badRequest().body(error);
        }
    }
}
