package com.egertj.fooddeliveryfeeapplication.businessrules.repository;

import com.egertj.fooddeliveryfeeapplication.businessrules.model.ExtraFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtraFeeRepository extends JpaRepository<ExtraFee, Long> {

    boolean existsByPhenomenonAndVehicleType(String phenomenon, String vehicleType);

    List<ExtraFee> getExtraFeeByConditionAndVehicleType(String condition, String vehicleType);
}
