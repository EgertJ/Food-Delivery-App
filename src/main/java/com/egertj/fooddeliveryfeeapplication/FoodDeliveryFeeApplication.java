package com.egertj.fooddeliveryfeeapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FoodDeliveryFeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodDeliveryFeeApplication.class, args);
	}

}
