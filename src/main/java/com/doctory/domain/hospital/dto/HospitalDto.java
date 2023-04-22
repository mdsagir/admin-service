package com.doctory.domain.hospital.dto;

import com.doctory.infra.entity.Address;
import com.doctory.infra.entity.Hospital;

public record HospitalDto(Long id, String hospitalName, String foundedAt, String addressLine1, String addressLine2,
                          String pinCode, String state, String country) {
    public static HospitalDto of(Hospital hospital) {
        Address address = hospital.getAddress();
        return new HospitalDto(hospital.getId(), hospital.getHospitalName(), hospital.getFoundedAt(), address.getAddressLine1(), address.getAddressLine2(), address.getPinCode(), address.getState(), address.getCountry());
    }
}
