package com.doctory.infra.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;


@Entity
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hospitalName;
    private String foundedAt;
    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHospitalName()
    {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Address getAddress() {
        return address;
    }

    public String getFoundedAt() {
        return foundedAt;
    }

    public void setFoundedAt(String foundedAt) {
        this.foundedAt = foundedAt;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
