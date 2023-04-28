package com.doctory.web.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record BranchRequest(
        @NotNull(message = "The hospital id must be defined")
        Long hospitalId,
        String branchName,
        @Valid
        AddressRequest addressRequest
) {
}
