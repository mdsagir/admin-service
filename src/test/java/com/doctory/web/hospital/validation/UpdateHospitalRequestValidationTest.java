package com.doctory.web.hospital.validation;

import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.HospitalRequest;
import com.doctory.web.request.UpdateHospitalRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.assertj.core.api.Assertions.assertThat;

class UpdateHospitalRequestValidationTest {

    private static Validator validator;
    private static AddressRequest addressRequest;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
    }

    @Test
    void when_all_field_correct_then_validation_succeed() {
        var hospitalRequest = new UpdateHospitalRequest(110L,"AK Hospital", "1989", addressRequest);
        Set<ConstraintViolation<UpdateHospitalRequest>> validate = validator.validate(hospitalRequest);
        assertThat(validate).isEmpty();
    }



    @ParameterizedTest
    @ValueSource(longs = {-1})
    void when_id_field_define_negative_then_validation_failed(Long id) {
        var updateHospitalRequest = new UpdateHospitalRequest(id,"AK Hospital", "1989", addressRequest);
        Set<ConstraintViolation<UpdateHospitalRequest>> validate = validator.validate(updateHospitalRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The hospital id must be positive no");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_hospitalName_field_define_empty_and_null_then_validation_failed(String hospitalName) {
        var updateHospitalRequest = new UpdateHospitalRequest(101L,hospitalName, "1989", addressRequest);
        Set<ConstraintViolation<UpdateHospitalRequest>> validate = validator.validate(updateHospitalRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The hospital name must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_founded_field_define_empty_and_null_then_validation_failed(String founded) {
        var updateHospitalRequest = new UpdateHospitalRequest(101L,"AK Hospital", founded, addressRequest);
        Set<ConstraintViolation<UpdateHospitalRequest>> validate = validator.validate(updateHospitalRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The founded must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }

}
