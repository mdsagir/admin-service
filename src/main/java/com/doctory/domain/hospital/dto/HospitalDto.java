package com.doctory.domain.hospital.dto;

import com.doctory.infra.entity.Address;
import com.doctory.infra.entity.Hospital;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record HospitalDto(Long id, String hospitalName, String foundedAt, String addressLine1, String addressLine2,
                          String pinCode, String state, String country,
                          @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime created,
                          @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime modified) {

    public HospitalDto(Hospital hospital) {
        this(hospital.getId(), hospital.getHospitalName(), hospital.getFoundedAt(), hospital.getAddress().getAddressLine1(), hospital.getAddress().getAddressLine2(), hospital.getAddress().getPinCode(), hospital.getAddress().getState(), hospital.getAddress().getCountry(), hospital.getCommon().getCreatedDate(), hospital.getCommon().getModifiedDate());
    }

    public static HospitalDto of(Hospital hospital) {
        Address address = hospital.getAddress();
        return new HospitalDto(hospital.getId(), hospital.getHospitalName(), hospital.getFoundedAt(), address.getAddressLine1(), address.getAddressLine2(), address.getPinCode(), address.getState(), address.getCountry(), hospital.getCommon().getCreatedDate(), hospital.getCommon().getModifiedDate());
    }
}
