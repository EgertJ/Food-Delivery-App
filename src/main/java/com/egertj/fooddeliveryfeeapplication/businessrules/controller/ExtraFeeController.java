package com.egertj.fooddeliveryfeeapplication.businessrules.controller;

import com.egertj.fooddeliveryfeeapplication.businessrules.exception.BusinessRuleException;
import com.egertj.fooddeliveryfeeapplication.businessrules.model.ExtraFee;
import com.egertj.fooddeliveryfeeapplication.businessrules.service.ExtraFeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/extra-fees")
public class ExtraFeeController {

    private static final Logger logger = LoggerFactory.getLogger(ExtraFeeController.class);
    @Autowired
    private final ExtraFeeService extraFeeService;

    public ExtraFeeController(ExtraFeeService extraFeeService) {
        this.extraFeeService = extraFeeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ExtraFee>> getExtraFees(){
        List<ExtraFee> extraFees = extraFeeService.getAllExtraFeeData();
        logger.info("Getting all extra fees");
        return ResponseEntity.ok(extraFees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ExtraFee>> getExtraFee(@PathVariable(value = "id") Long id){
        Optional<ExtraFee> extraFee = extraFeeService.getExtraFeeById(id);
        if (extraFee.isEmpty()){
            logger.error("Extra fee not found.");
            return ResponseEntity.notFound().build();
        }
        logger.info("Getting extra fee");
        return ResponseEntity.ok(extraFee);
    }

    @PostMapping("/")
    public ResponseEntity<?> createExtraFee(@RequestBody ExtraFee extraFee){
        try{
            extraFeeService.addExtraFeeData(extraFee);
            logger.info("Creating extra fee");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BusinessRuleException e) {
            logger.error("Error creating extra fee" + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExtraFee> updateExtraFee(@PathVariable(value="id") Long id, @RequestBody ExtraFee extraFeeDetails){
        Optional<ExtraFee> extraFee = extraFeeService.getExtraFeeById(id);
        if(extraFee.isEmpty()){
            logger.error("Extra fee not found.");
            return ResponseEntity.notFound().build();
        }
        extraFee.get().setCondition(extraFeeDetails.getCondition());
        extraFee.get().setVehicleType(extraFeeDetails.getVehicleType());
        extraFee.get().setFee(extraFeeDetails.getFee());
        extraFee.get().setTemperature_lower_bound(extraFeeDetails.getTemperature_lower_bound());
        extraFee.get().setTemperature_upper_bound(extraFeeDetails.getTemperature_upper_bound());
        extraFee.get().setWind_speed_lower_bound(extraFeeDetails.getWind_speed_lower_bound());
        extraFee.get().setWind_speed_upper_bound(extraFeeDetails.getWind_speed_upper_bound());
        extraFee.get().setPhenomenon(extraFeeDetails.getPhenomenon());
        ExtraFee updatedExtraFee = extraFeeService.updateExtraFee(extraFee.get());
        logger.info("Updated extra fee");
        return ResponseEntity.ok(updatedExtraFee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExtraFee(@PathVariable(value = "id") Long id){
        Optional<ExtraFee> extraFee = extraFeeService.getExtraFeeById(id);
        if (extraFee.isEmpty()){
            logger.error("Extra fee not found.");
            return ResponseEntity.notFound().build();
        }
        logger.info("Deleted extra fee");
        extraFeeService.deleteExtraFee(id);
        return ResponseEntity.ok().build();
    }
}
