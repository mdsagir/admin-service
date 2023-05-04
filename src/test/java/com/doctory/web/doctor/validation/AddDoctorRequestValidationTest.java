package com.doctory.web.doctor.validation;

import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.DoctorRequest;
import com.doctory.web.request.HospitalRequest;
import com.doctory.web.request.PersonRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static com.doctory.web.request.PersonRequest.of;
import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.assertj.core.api.Assertions.assertThat;

class AddDoctorRequestValidationTest {

    private static Validator validator;
    private static AddressRequest addressRequest;
    private static PersonRequest personRequest;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        personRequest = of("AA", "BB", "CC", "908979780", "2343214532", "aa@gmail.com");
    }

    @Test
    void when_doctor_all_field_correct_then_validation_succeed() {

        var doctorRequest = new DoctorRequest(1L, "SS Rahul", "D", "surgeon", "heart", addressRequest, personRequest);
        Set<ConstraintViolation<DoctorRequest>> validate = validator.validate(doctorRequest);
        assertThat(validate).isEmpty();
    }
    @Test
    void when_branchId_field_define_null_then_validation_failed() {
        var doctorRequest = new DoctorRequest(null, "SS Rahul", "D", "surgeon", "heart", addressRequest, personRequest);
        Set<ConstraintViolation<DoctorRequest>> validate = validator.validate(doctorRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The branch id must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }
    @Test
    void when_branchId_field_define_negative_then_validation_failed() {
        var doctorRequest = new DoctorRequest(-1L, "SS Rahul", "D", "surgeon", "heart", addressRequest, personRequest);
        Set<ConstraintViolation<DoctorRequest>> validate = validator.validate(doctorRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The branch id must be positive no");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }
    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_doctor_name_field_define_empty_or_null_then_validation_failed(String doctorName) {
        var doctorRequest = new DoctorRequest(1L, doctorName, "D", "surgeon", "heart", addressRequest, personRequest);
        Set<ConstraintViolation<DoctorRequest>> validate = validator.validate(doctorRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The doctor name must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }
    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_doctor_degree_field_define_empty_or_null_then_validation_failed(String doctorDegree) {
        var doctorRequest = new DoctorRequest(1L, "A", doctorDegree, "surgeon", "heart", addressRequest, personRequest);
        Set<ConstraintViolation<DoctorRequest>> validate = validator.validate(doctorRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The doctor degree must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_doctor_practice_field_define_empty_or_null_then_validation_failed(String practiceName) {
        var doctorRequest = new DoctorRequest(1L, "A", "D", practiceName, "heart", addressRequest, personRequest);
        Set<ConstraintViolation<DoctorRequest>> validate = validator.validate(doctorRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The doctor practice must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }
    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void when_doctor_specialist_field_define_empty_or_null_then_validation_failed(String specialist) {
        var doctorRequest = new DoctorRequest(1L, "A", "D", "P", specialist, addressRequest, personRequest);
        Set<ConstraintViolation<DoctorRequest>> validate = validator.validate(doctorRequest);
        assertThat(validate).hasSize(1);
        boolean equals = validate.iterator().next().getMessage().equals("The doctor specialist must be defined");
        assertThat(validate).hasSize(1);
        assertThat(equals).isEqualTo(Boolean.TRUE);
    }
}
