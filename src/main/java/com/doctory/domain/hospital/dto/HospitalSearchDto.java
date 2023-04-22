package com.doctory.domain.hospital.dto;

public class HospitalSearchDto {
    private Long id;
    private String name;

    public HospitalSearchDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public HospitalSearchDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
