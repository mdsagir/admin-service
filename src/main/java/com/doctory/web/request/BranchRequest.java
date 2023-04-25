package com.doctory.web.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record BranchRequest(
        @NotBlank(message = "The hospital id must be defined")
        Long hospitalId,
        String branchName,
        @Valid
        AddressRequest addressRequest
) {
}
