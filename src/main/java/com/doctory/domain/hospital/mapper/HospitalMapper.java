package com.doctory.domain.hospital.mapper;


import com.doctory.domain.hospital.dto.HospitalDto;
import com.doctory.infra.entity.Address;
import com.doctory.infra.entity.Common;
import com.doctory.infra.entity.Hospital;
import com.doctory.web.request.HospitalRequest;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class HospitalMapper {

    public Hospital toHospitalEntity(HospitalRequest hospitalRequest) {


        LocalDateTime localDateTime = currentDate();

        Common common=new Common();
        common.setCreatedBy(1L);
        common.setModifiedBy(1L);
        common.setCreatedDate(localDateTime);
        common.setModifiedDate(localDateTime);

        Address address=new Address();
        address.setAddressLine1(hospitalRequest.addressLine1());
        address.setAddressLine2(hospitalRequest.addressLine2());
        address.setState(hospitalRequest.state());
        address.setCountry(hospitalRequest.country());
        address.setPinCode(hospitalRequest.pinCode());

        Hospital hospital=new Hospital();
        hospital.setHospitalName(hospitalRequest.hospitalName());
        hospital.setFoundedAt(hospitalRequest.foundedAt());
        hospital.setAddress(address);
        hospital.setCommon(common);

        return hospital;
    }

    public HospitalDto toHospitalDto(Hospital hospital) {
        return HospitalDto.of(hospital);
    }
    public Hospital toUpdateHospitalEntity(HospitalRequest hospitalRequest,Hospital hospital) {

        Address address= hospital.getAddress();
        address.setAddressLine1(hospitalRequest.addressLine1());
        address.setAddressLine2(hospitalRequest.addressLine2());
        address.setState(hospitalRequest.state());
        address.setCountry(hospitalRequest.country());
        address.setPinCode(hospitalRequest.pinCode());

        hospital.setHospitalName(hospitalRequest.hospitalName());
        hospital.setFoundedAt(hospitalRequest.foundedAt());
        hospital.setAddress(address);
        return hospital;
    }

    private LocalDateTime currentDate() {
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.now(zoneId);
    }
}
