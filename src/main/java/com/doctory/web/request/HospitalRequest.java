package com.doctory.web.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record HospitalRequest(
        @NotBlank(message = "The hospital name must be defined")
        String hospitalName,
        @NotBlank(message = "The founded must be defined")
        String foundedAt,
        @Valid
        AddressRequest addressRequest
) {
        public HospitalRequest() {
                this(null,null,null);
        }
}
