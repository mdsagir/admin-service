package com.doctory.web.request;

import jakarta.validation.constraints.NotBlank;

public record PersonRequest(
        @NotBlank(message = "The first name must be defined")
        String firstName,
        String lastName,
        String surname,
        @NotBlank(message = "The mobile must be defined")
        String mobileNo,
        String alternateNo,
        String email) {
        public static PersonRequest of(String firstName, String lastName, String surname, String mobileNo, String alternateNo, String email) {
                return new PersonRequest(firstName,lastName,surname,mobileNo,alternateNo,email);
        }
}
