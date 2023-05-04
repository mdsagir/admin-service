package com.doctory.web.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DoctorRequest(
        @NotNull(message = "The branch id must be defined")
        @Positive(message = "The branch id must be positive no")
        Long branchId,
        @NotBlank(message = "The doctor degree must be defined")
        String doctorDegree,
        @NotBlank(message = "The doctor practice must be defined")
        String practiceName,
        @NotBlank(message = "The doctor specialist must be defined")
        String specialist,
        @Valid
        AddressRequest addressRequest,
        @Valid
        PersonRequest personRequest
) {
    public static DoctorRequest of(long branchId, String doctorDegree, String practiceName, String specialist, AddressRequest addressRequest, PersonRequest personRequest) {

        return new DoctorRequest(branchId, doctorDegree, practiceName, specialist, addressRequest, personRequest);
    }
}
