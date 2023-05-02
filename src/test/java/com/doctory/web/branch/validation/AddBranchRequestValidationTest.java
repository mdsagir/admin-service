package com.doctory.web.branch.validation;

import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.BranchRequest;
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

class AddBranchRequestValidationTest {

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
        var hospitalRequest = new BranchRequest(101L,"Port Luis", addressRequest);
        Set<ConstraintViolation<BranchRequest>> validate = validator.validate(hospitalRequest);
        assertThat(validate).isEmpty();
    }
    @NullSource
    @ParameterizedTest
    void when_hospital_id_field_define_null_then_validation_failed(Long hospitalId) {
        var hospitalRequest = new BranchRequest(hospitalId,"Port Luis", addressRequest);
        Set<ConstraintViolation<BranchRequest>> validate = validator.validate(hospitalRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The hospital id must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }
    @ParameterizedTest
    @ValueSource(longs = -1)
    void when_hospital_id_field_define_empty_then_validation_failed(Long hospitalId) {
        var hospitalRequest = new BranchRequest(hospitalId,"Port Luis", addressRequest);
        Set<ConstraintViolation<BranchRequest>> validate = validator.validate(hospitalRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The hospital id must be positive no");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"","  "})
    void when_branch_name_field_define_empty_then_validation_failed(String branchName) {
        var hospitalRequest = new BranchRequest(1L,branchName, addressRequest);
        Set<ConstraintViolation<BranchRequest>> validate = validator.validate(hospitalRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The branch name must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }
}
