package com.doctory.web.request;

import jakarta.validation.constraints.NotBlank;

public record HospitalRequest(
        @NotBlank(message = "The hospital name must be defined")
        String hospitalName,
        @NotBlank(message = "The founded must be defined")
        String foundedAt,
        @NotBlank(message = "The address must be defined")
        String addressLine1,
        String addressLine2,
        @NotBlank(message = "The pin code must be defined")
        String pinCode,
        @NotBlank(message = "The state must be defined")
        String state,
        @NotBlank(message = "The country must be defined")
        String country
) {
}
