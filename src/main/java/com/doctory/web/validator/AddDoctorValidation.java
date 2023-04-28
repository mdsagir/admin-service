package com.doctory.web.validator;

import com.doctory.infra.repo.DoctorRepo;
import com.doctory.web.request.DoctorRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddDoctorValidation implements Validator {

    private final DoctorRepo doctorRepo;

    public AddDoctorValidation(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return DoctorRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target,@NonNull Errors errors) {
        var doctorRequest = (DoctorRequest) target;
        var doctorName = doctorRequest.doctorName();
        var doctorOptional = doctorRepo.findByPerson_FirstName(doctorName);
        doctorOptional.ifPresent(doctor -> errors.rejectValue("doctorName", "doctorName.already", doctor.getPerson().getFirstName() + " doctor name already exist"));

    }
}
