package com.doctory.web.doctor.validation;


import com.doctory.infra.entity.Doctor;
import com.doctory.infra.entity.Person;
import com.doctory.infra.repo.DoctorRepo;
import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.DoctorRequest;
import com.doctory.web.request.PersonRequest;
import com.doctory.web.validator.AddDoctorValidation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class DoctorNameExistValidationTest {

    @Autowired
    private AddDoctorValidation addDoctorValidation;
    @MockBean
    private DoctorRepo doctorRepo;

    @Test
    void when_doctor_name_exist_then_validation_failed() {

        String doctorName = "AK Agrawal";

        var personRequest = PersonRequest.of(doctorName, "BB", "CC", "908979780", "2343214532", "aa@gmail.com");
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");

        var doctorRequest = new DoctorRequest(1L, "D", "surgeon", "heart", addressRequest, personRequest);

        var person = new Person();
        person.setFirstName(doctorName);
        var doctor = new Doctor();
        doctor.setPerson(person);

        given(doctorRepo.findByPerson_FirstName(doctorRequest.personRequest().firstName())).willReturn(Optional.of(doctor));
        Errors errors = new BeanPropertyBindingResult(doctorRequest, "");
        addDoctorValidation.validate(doctorRequest, errors);
        assertThat(errors.hasErrors()).isTrue();
        var fieldError = errors.getFieldError();
        assertThat(fieldError).isNotNull();
        var errorMessage = errors.getFieldError().getDefaultMessage();
        assertThat(errorMessage).isEqualTo(doctorName + " doctor name already exist");
    }

    @Test
    void when_doctor_name_not_exist_then_validation_success() {

        var personRequest = PersonRequest.of("AK Agrawal", "BB", "CC", "908979780", "2343214532", "aa@gmail.com");
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");

        var doctorRequest = new DoctorRequest(1L, "D", "surgeon", "heart", addressRequest, personRequest);
        given(doctorRepo.findByPerson_FirstName(doctorRequest.personRequest().firstName())).willReturn(Optional.empty());
        Errors errors = new BeanPropertyBindingResult(doctorRequest, "");
        addDoctorValidation.validate(doctorRequest, errors);
        assertThat(errors.hasErrors()).isFalse();
    }
}
