package com.doctory.web.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record DoctorRequest(
        @NotBlank(message = "The hospital id must be defined")
        Long branchId,
        String doctorName,
        String doctorDegree,
        String practiceName,
        String specialist,
        @Valid
        AddressRequest addressRequest,
        @Valid
        PersonRequest personRequest
) {
}
