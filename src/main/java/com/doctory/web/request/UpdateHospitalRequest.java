package com.doctory.web.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateHospitalRequest(
        @NotNull(message = "The hospital id must be defined")
        @Positive(message = "The hospital id must be positive no")
        Long id,
        @NotBlank(message = "The hospital name must be defined")
        String hospitalName,
        @NotBlank(message = "The founded must be defined")
        String foundedAt,
        @Valid
        AddressRequest addressRequest
) {
}
