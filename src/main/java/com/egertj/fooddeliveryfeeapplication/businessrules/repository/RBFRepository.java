package com.egertj.fooddeliveryfeeapplication.businessrules.repository;

import com.egertj.fooddeliveryfeeapplication.businessrules.model.RBF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RBFRepository extends JpaRepository<RBF, Long> {
    boolean existsByCityAndVehicleType(String city, String vehicleType);

    RBF getRBFByCityAndVehicleType(String city, String vehicleType);


}
