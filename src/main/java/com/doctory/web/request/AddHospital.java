package com.doctory.web.request;

public record AddHospital(
        String hospitalName,
        String foundedAt,
        String addressLine1,
        String addressLine2,
        String pinCode,
        String state,
        String country
) {
}
