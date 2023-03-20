package com.egertj.fooddeliveryfeeapplication.businessrules.controller;


import com.egertj.fooddeliveryfeeapplication.businessrules.exception.BusinessRuleException;
import com.egertj.fooddeliveryfeeapplication.businessrules.model.RBF;
import com.egertj.fooddeliveryfeeapplication.businessrules.service.RBFService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rbf-fees")
public class RBFController {

    @Autowired
    private final RBFService rbfService;

    private static final Logger logger = LoggerFactory.getLogger(RBFController.class);

    public RBFController(RBFService rbfService) {
        this.rbfService = rbfService;
    }

    @GetMapping("/")
    public ResponseEntity<List<RBF>> getRBFFees(){
        List<RBF> rbfList = rbfService.getAllRBFData();
        logger.info("Getting all rbf fees.");
        return ResponseEntity.ok(rbfList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<RBF>> getRBFFee(@PathVariable(value = "id") Long id){
        Optional<RBF> rbfFee = rbfService.getRBFById(id);
        if (rbfFee.isEmpty()){
            logger.error("RBF not found.");
            return ResponseEntity.notFound().build();
        }
        logger.info("Getting rbf fee");
        return ResponseEntity.ok(rbfFee);
    }

    @PostMapping("/")
    public ResponseEntity<?> createRBF(@RequestBody RBF rbf){
        try {
            rbfService.addRBFData(rbf);
            logger.info("Creating rbf fee");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BusinessRuleException e) {
            logger.error("Error creating rbf fee" + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RBF> updateRBF(@PathVariable(value = "id") Long id, @RequestBody RBF rbfData){
        Optional<RBF> rbfFee = rbfService.getRBFById(id);
        if(rbfFee.isEmpty()){
            logger.error("Rbf does not exist");
            return ResponseEntity.notFound().build();
        }
        rbfFee.get().setCity(rbfData.getCity());
        rbfFee.get().setVehicleType(rbfData.getVehicleType());
        rbfFee.get().setFee(rbfData.getFee());
        RBF UpdatedRBFFee = rbfService.updateRBF(rbfFee.get());
        logger.info("Updating rbf fee");
        return ResponseEntity.ok(UpdatedRBFFee);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRBF(@PathVariable(value = "id") Long id){
        Optional<RBF> rbf = rbfService.getRBFById(id);
        if (rbf.isEmpty()) {
            logger.error("Rbf does not exist");
            return ResponseEntity.notFound().build();
        }
        rbfService.deleteRBF(id);
        logger.info("Deleted rbf fee");
        return ResponseEntity.ok().build();
    }


}
