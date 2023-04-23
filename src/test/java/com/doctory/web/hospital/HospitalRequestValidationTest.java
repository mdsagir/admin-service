package com.doctory.web.hospital;

import com.doctory.web.request.HospitalRequest;
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

class HospitalRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void when_all_field_correct_then_validation_succeed() {
        var hospitalRequest = new HospitalRequest("AK Hospital", "1989", "Address1", "Address2", "898765", "Bihar", "India");
        Set<ConstraintViolation<HospitalRequest>> validate = validator.validate(hospitalRequest);
        assertThat(validate).isEmpty();
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_hospitalName_field_define_empty_and_null_then_validation_failed(String hospitalName) {
        var hospitalRequest = new HospitalRequest(hospitalName, "1989", "Address1", "Address2", "898765", "Bihar", "India");
        Set<ConstraintViolation<HospitalRequest>> validate = validator.validate(hospitalRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The hospital name must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_founded_field_define_empty_and_null_then_validation_failed(String founded) {
        var hospitalRequest = new HospitalRequest("AK Hospital", founded, "Address1", "Address2", "898765", "Bihar", "India");
        Set<ConstraintViolation<HospitalRequest>> validate = validator.validate(hospitalRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The founded must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_address1_field_define_empty_then_validation_failed(String addressLine1) {
        var hospitalRequest = new HospitalRequest("AK Hospital", "1999", addressLine1, "Address2", "898765", "Bihar", "India");
        Set<ConstraintViolation<HospitalRequest>> validate = validator.validate(hospitalRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The address must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_pin_code_field_define_empty_and_null_then_validation_failed(String pinCode) {
        var hospitalRequest = new HospitalRequest("AK Hospital", "1999", "Address1", "Address2", pinCode, "Bihar", "India");
        Set<ConstraintViolation<HospitalRequest>> validate = validator.validate(hospitalRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The pin code must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_address2_field_define_empty_and_null_then_validation_success(String addressLine2) {
        var hospitalRequest = new HospitalRequest("AK Hospital", "1999", "Address1", addressLine2, "898765", "Bihar", "India");
        Set<ConstraintViolation<HospitalRequest>> validate = validator.validate(hospitalRequest);
        assertThat(validate).isEmpty();
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_state_field_define_empty_and_null_then_validation_failed(String state) {
        var hospitalRequest = new HospitalRequest("AK Hospital", "1999", "Address1", "Address2", "767676", state, "India");
        Set<ConstraintViolation<HospitalRequest>> validate = validator.validate(hospitalRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The state must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_country_field_define_empty_and_null_then_validation_failed(String country) {
        var hospitalRequest = new HospitalRequest("AK Hospital", "1999", "Address1", "Address2", "76767", "Bihar", country);
        Set<ConstraintViolation<HospitalRequest>> validate = validator.validate(hospitalRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The country must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }

}
