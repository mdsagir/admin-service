package com.doctory.infra.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;

@Entity
public class Doctor {

    @Id
    private Long id;
    private String degree;
    private String practiceName;
    private String specialty;
    @MapsId
    @JoinColumn(name = "id")
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Person person;
    @JoinColumn(name = "hospital_id")
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Branch branch;
    @Embedded
    private Common common;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Common getCommon() {
        return common;
    }

    public void setCommon(Common common) {
        this.common = common;
    }
}
