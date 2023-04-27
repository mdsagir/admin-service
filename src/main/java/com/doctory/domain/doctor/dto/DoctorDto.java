package com.doctory.domain.doctor.dto;

import com.doctory.infra.entity.Doctor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DoctorDto(Long id, Long branchId, String degree, String practiceName, String specialty, String firstName,
                        String lastName, String surname, String mobileNo, String alternateNo, String email,
                        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime created,
                        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime modified) {
    public DoctorDto(Doctor doctor) {
        this(doctor.getId(), doctor.getBranch().getId(), doctor.getDegree(), doctor.getPracticeName(), doctor.getSpecialty(),
                doctor.getPerson().getFirstName(), doctor.getPerson().getLastName(), doctor.getPerson().getSurname(),
                doctor.getPerson().getMobileNo(), doctor.getPerson().getAlternateNo(), doctor.getPerson().getEmail(),
                doctor.getCommon().getCreatedDate(), doctor.getCommon().getModifiedDate());
    }
    public static DoctorDto of(Doctor doctor) {
        return new DoctorDto(doctor.getId(), doctor.getBranch().getId(), doctor.getDegree(), doctor.getPracticeName(), doctor.getSpecialty(),
                doctor.getPerson().getFirstName(), doctor.getPerson().getLastName(), doctor.getPerson().getSurname(),
                doctor.getPerson().getMobileNo(), doctor.getPerson().getAlternateNo(), doctor.getPerson().getEmail(),
                doctor.getCommon().getCreatedDate(), doctor.getCommon().getModifiedDate());
    }
}
