package com.doctory.domain.hospital.mapper;


import com.doctory.infra.entity.Address;
import com.doctory.infra.entity.Hospital;
import com.doctory.web.request.AddHospital;
import org.springframework.stereotype.Component;

@Component
public class HospitalMapper {

    public Hospital toHospitalEntity(AddHospital addHospital) {

        Address address=new Address();
        address.setAddressLine1(addHospital.addressLine1());
        address.setAddressLine2(addHospital.addressLine2());
        address.setState(addHospital.state());
        address.setCountry(addHospital.country());
        address.setPinCode(addHospital.pinCode());

        Hospital hospital=new Hospital();
        hospital.setHospitalName(addHospital.hospitalName());
        hospital.setFoundedAt(addHospital.foundedAt());
        hospital.setAddress(address);
        return hospital;
    }
}
