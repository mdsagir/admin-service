package com.doctory.web.hospital;

import com.doctory.web.request.HospitalRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class HospitalRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void when_all_field_correct_then_validation_succeed() {
        var hospitalRequest = new HospitalRequest("AK Hospital", "1989",
                "Address1", "Address2", "898765", "Bihar", "India");
        Set<ConstraintViolation<HospitalRequest>> validate = validator.validate(hospitalRequest);
        assertThat(validate).isEmpty();
    }
    @Test
    void when_hospitalName_field_not_define_then_validation_failed() {
        var hospitalRequest = new HospitalRequest("", "1989",
                "Address1", "Address2", "898765", "Bihar", "India");
        Set<ConstraintViolation<HospitalRequest>> validate = validator.validate(hospitalRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The hospital name must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }
}
