package com.doctory.web.hospital.validation;


import com.doctory.infra.entity.Hospital;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.HospitalRequest;
import com.doctory.web.validator.AddHospitalValidator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;


@SpringBootTest
class HospitalCheckHotelNameExistValidationTest {

    @Autowired
    private AddHospitalValidator addHospitalValidator;
    @MockBean
    private HospitalRepo hospitalRepo;

    @Test
    void test_hospital_name_exist_then_validation_failed() {
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");

        var hospitalRequest = new HospitalRequest("AJ Hospital", "1989", addressRequest);
        var hospital = new Hospital();
        hospital.setHospitalName("AJ Hospital");
        given(hospitalRepo.findByHospitalName(hospitalRequest.hospitalName())).willReturn(Optional.of(hospital));
        Errors errors = new BeanPropertyBindingResult(hospitalRequest, "");
        addHospitalValidator.validate(hospitalRequest, errors);
        assertThat(errors.hasErrors()).isTrue();
        var fieldError = errors.getFieldError();
        assertThat(fieldError).isNotNull();
        var errorMessage = errors.getFieldError().getDefaultMessage();
        assertThat(errorMessage).isEqualTo("AJ Hospital hospital name already exist");
    }

    @Test
    void test_hospital_name_not_exist_then_validation_success() {
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");

        var hospitalRequest = new HospitalRequest("AJ Hospital", "1989", addressRequest);
        given(hospitalRepo.findByHospitalName(hospitalRequest.hospitalName())).willReturn(Optional.empty());
        Errors errors = new BeanPropertyBindingResult(hospitalRequest, "");
        addHospitalValidator.validate(hospitalRequest, errors);
        assertThat(errors.hasErrors()).isFalse();
    }
}
