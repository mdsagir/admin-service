package com.doctory.web.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateHospitalRequest(
        @NotNull(message = "The hospital name must be defined")
        Long id,
        @NotBlank(message = "The hospital name must be defined")
        String hospitalName,
        @NotBlank(message = "The founded must be defined")
        String foundedAt,
        @Valid
        AddressRequest addressRequest
) {
}
