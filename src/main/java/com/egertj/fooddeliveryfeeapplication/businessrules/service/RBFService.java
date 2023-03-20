package com.egertj.fooddeliveryfeeapplication.businessrules.service;

import com.egertj.fooddeliveryfeeapplication.businessrules.exception.BusinessRuleException;
import com.egertj.fooddeliveryfeeapplication.businessrules.model.RBF;
import com.egertj.fooddeliveryfeeapplication.businessrules.repository.RBFRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RBFService {

    @Autowired
    private final RBFRepository repository;

    public RBFService(RBFRepository repository) {
        this.repository = repository;
    }

    public List<RBF> getAllRBFData(){
        return  repository.findAll();
    }

    public RBF addRBFData(RBF rbf) throws BusinessRuleException {
        if (!repository.existsByCityAndVehicleType(rbf.getCity(), rbf.getVehicleType()))
            return repository.save(rbf);
        else throw new BusinessRuleException("There already exists a rule with the same city and vehicle type");
    }

    public Optional<RBF> getRBFById(Long id){
        return repository.findById(id);
    }

    public RBF updateRBF(RBF rbf){
        return repository.save(rbf);
    }

    public void deleteRBF(Long id){
        repository.deleteById(id);
    }

    public RBF getRBFByCityAndVehicleType(String city, String vehicleType){
        return repository.getRBFByCityAndVehicleType(city,vehicleType);
    }
}
