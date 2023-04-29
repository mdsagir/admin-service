package com.doctory.web.hospital.validation;

import com.doctory.web.request.AddressRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.assertj.core.api.Assertions.assertThat;

class AddressRequestValidationTest {

    private static Validator validator;


    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }
    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_address1_field_define_empty_then_validation_failed(String addressLine1) {
        var newAddressRequest = new AddressRequest(addressLine1, "Address2", "898765", "Bihar", "India");
        Set<ConstraintViolation<AddressRequest>> validate = validator.validate(newAddressRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The address must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_pin_code_field_define_empty_and_null_then_validation_failed(String pinCode) {
        var newAddressRequest = new AddressRequest("address1", "Address2", pinCode, "Bihar", "India");
        Set<ConstraintViolation<AddressRequest>> validate = validator.validate(newAddressRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The pin code must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_address2_field_define_empty_and_null_then_validation_success(String addressLine2) {
        var newAddressRequest = new AddressRequest("address1", addressLine2, "23233", "Bihar", "India");
        Set<ConstraintViolation<AddressRequest>> validate = validator.validate(newAddressRequest);
        assertThat(validate).isEmpty();
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_state_field_define_empty_and_null_then_validation_failed(String state) {
        var newAddressRequest = new AddressRequest("address1", "address2", "23233", state, "India");
        Set<ConstraintViolation<AddressRequest>> validate = validator.validate(newAddressRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The state must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_country_field_define_empty_and_null_then_validation_failed(String country) {
        var newAddressRequest = new AddressRequest("address1", "address2", "23233", "bihar", country);
        Set<ConstraintViolation<AddressRequest>> validate = validator.validate(newAddressRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The country must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }

}
