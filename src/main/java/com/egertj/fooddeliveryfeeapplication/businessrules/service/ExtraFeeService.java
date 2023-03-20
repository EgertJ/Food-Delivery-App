package com.egertj.fooddeliveryfeeapplication.businessrules.service;

import com.egertj.fooddeliveryfeeapplication.businessrules.exception.BusinessRuleException;
import com.egertj.fooddeliveryfeeapplication.businessrules.model.ExtraFee;
import com.egertj.fooddeliveryfeeapplication.businessrules.repository.ExtraFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ExtraFeeService {


    @Autowired
    private final ExtraFeeRepository repository;


    public ExtraFeeService(ExtraFeeRepository repository) {
        this.repository = repository;
    }

    public List<ExtraFee> getAllExtraFeeData() {
        return repository.findAll();
    }

    public ExtraFee addExtraFeeData(ExtraFee extraFee) throws BusinessRuleException {

        if (!repository.existsByPhenomenonAndVehicleType(extraFee.getCondition(), extraFee.getVehicleType()))
            return repository.save(extraFee);
        else throw new BusinessRuleException("There already exists a rule with the same phenomenon and vehicle type");
    }

    public Optional<ExtraFee> getExtraFeeById(Long id){
        return repository.findById(id);
    }

    public ExtraFee updateExtraFee(ExtraFee extraFee) {
        return repository.save(extraFee);
    }

    public void deleteExtraFee(Long id) {
        repository.deleteById(id);
    }

    public List<ExtraFee> getExtraFeeByConditionAndVehicleType(String condition, String vehicleType) {
        return repository.getExtraFeeByConditionAndVehicleType(condition, vehicleType);
    }
}
