package com.doctory.web.validator;

import com.doctory.infra.entity.Hospital;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.AddHospital;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class AddHospitalValidator implements Validator {

    private final HospitalRepo hospitalRepo;

    public AddHospitalValidator(HospitalRepo hospitalRepo) {
        this.hospitalRepo = hospitalRepo;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return AddHospital.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object object,@NonNull Errors errors) {
        AddHospital hospitalRequest = (AddHospital) object;
        String hospitalName = hospitalRequest.hospitalName();
        Optional<Hospital> hospitalOptional = hospitalRepo.findByHospitalName(hospitalName);
        hospitalOptional.ifPresent(hospital -> errors.reject("Hospital name already exist"));
    }
}
