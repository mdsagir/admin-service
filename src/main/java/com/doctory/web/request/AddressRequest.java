package com.doctory.web.request;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(@NotBlank(message = "The address must be defined")
                             String addressLine1,
                             String addressLine2,
                             @NotBlank(message = "The pin code must be defined")
                             String pinCode,
                             @NotBlank(message = "The state must be defined")
                             String state,
                             @NotBlank(message = "The country must be defined")
                             String country) {
    public static AddressRequest of(String address1, String address2, String pinCode, String state, String country) {
        return new AddressRequest(address1,address2,pinCode,state,country);
    }
}
