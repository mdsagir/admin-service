package com.doctory.web.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateBranchRequest(
        @NotNull(message = "The branch id must be defined")
        @Positive(message = "The branch id must be positive no")
        Long branchId,
        @NotNull(message = "The hospital id must be defined")
        @Positive(message = "The hospital id must be positive no")
        Long hospitalId,
        @NotBlank(message = "The branch name must be defined")
        String branchName,
        @Valid
        AddressRequest addressRequest
) {
}
