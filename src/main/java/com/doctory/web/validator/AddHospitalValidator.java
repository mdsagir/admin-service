package com.doctory.web.validator;

import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.HospitalRequest;
import com.doctory.web.request.UpdateHospitalRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class AddHospitalValidator implements Validator {

    private static final Logger log = LoggerFactory.getLogger(AddHospitalValidator.class);


    private final HospitalRepo hospitalRepo;

    public AddHospitalValidator(HospitalRepo hospitalRepo) {
        this.hospitalRepo = hospitalRepo;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return HospitalRequest.class.isAssignableFrom(clazz) || UpdateHospitalRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object object, @NonNull Errors errors) {
        if (object instanceof HospitalRequest hospitalRequest) {
            var hospitalName = hospitalRequest.hospitalName();
            var hospitalOptional = hospitalRepo.findByHospitalName(hospitalName);
            hospitalOptional.ifPresent(hospital -> {
                log.error("hospitalName already exist in data base");
                errors.rejectValue("hospitalName", "field.already", hospital.getHospitalName() + " hospital name already exist");
            });
        }

    }
}
