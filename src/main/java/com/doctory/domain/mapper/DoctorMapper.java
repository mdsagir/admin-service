package com.doctory.domain.mapper;

import com.doctory.infra.entity.Doctor;
import com.doctory.infra.entity.Person;
import com.doctory.web.request.DoctorRequest;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    private final CommonMapper commonMapper;

    public DoctorMapper(CommonMapper commonMapper) {
        this.commonMapper = commonMapper;
    }

    public Doctor toDoctorEntity(DoctorRequest doctorRequest) {
        var addressRequest = doctorRequest.addressRequest();
        var address = commonMapper.toAddressEntity(addressRequest);
        var common = commonMapper.commonData();

        var doctor = new Doctor();
        doctor.setDegree(doctorRequest.doctorDegree());
        doctor.setPracticeName(doctorRequest.practiceName());
        doctor.setSpecialty(doctorRequest.practiceName());

        var personRequest = doctorRequest.personRequest();
        var person = new Person();
        person.setFirstName(personRequest.firstName());
        person.setLastName(personRequest.lastName());
        person.setSurname(personRequest.surname());
        person.setMobileNo(personRequest.mobileNo());
        person.setEmail(personRequest.email());
        person.setAlternateNo(personRequest.alternateNo());

        person.setAddress(address);
        doctor.setPerson(person);
        doctor.setCommon(common);
        return doctor;
    }

    public void toUpdateDoctorEntity(DoctorRequest doctorRequest, Doctor doctor) {

        var person = doctor.getPerson();

        var addressRequest = doctorRequest.addressRequest();
        var personRequest = doctorRequest.personRequest();
        commonMapper.updatePersonEntity(personRequest,addressRequest,person);

        doctor.setPerson(person);
        doctor.setDegree(doctorRequest.doctorDegree());
        doctor.setSpecialty(doctorRequest.specialist());
        doctor.setPracticeName(doctorRequest.practiceName());

    }


}
