package com.doctory.web.validator;

import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.HospitalRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class AddHospitalValidator implements Validator {

    private final HospitalRepo hospitalRepo;

    public AddHospitalValidator(HospitalRepo hospitalRepo) {
        this.hospitalRepo = hospitalRepo;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return HospitalRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object object,@NonNull Errors errors) {
        var hospitalRequest = (HospitalRequest) object;
        var hospitalName = hospitalRequest.hospitalName();
        var hospitalOptional = hospitalRepo.findByHospitalName(hospitalName);
        hospitalOptional.ifPresent(hospital -> errors.reject("Hospital name already exist"));
    }
}
