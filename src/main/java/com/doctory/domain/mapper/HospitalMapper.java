package com.doctory.domain.mapper;


import com.doctory.domain.hospital.dto.HospitalDto;
import com.doctory.infra.entity.Hospital;
import com.doctory.web.request.HospitalRequest;
import org.springframework.stereotype.Component;


@Component
public class HospitalMapper {

    private final CommonMapper commonMapper;

    public HospitalMapper(CommonMapper commonMapper) {
        this.commonMapper = commonMapper;
    }

    public Hospital toHospitalEntity(HospitalRequest hospitalRequest) {

        var addressRequest = hospitalRequest.addressRequest();
        var address = commonMapper.toAddressEntity(addressRequest);
        var common = commonMapper.commonData();

        Hospital hospital = new Hospital();
        hospital.setHospitalName(hospitalRequest.hospitalName());
        hospital.setFoundedAt(hospitalRequest.foundedAt());
        hospital.setAddress(address);
        hospital.setCommon(common);

        return hospital;
    }

    public HospitalDto toHospitalDto(Hospital hospital) {
        return HospitalDto.of(hospital);
    }

    public Hospital toUpdateHospitalEntity(HospitalRequest hospitalRequest, Hospital hospital) {

        var addressRequest = hospitalRequest.addressRequest();
        var address = hospital.getAddress();
        commonMapper.updateAddressEntity(addressRequest, address);

        hospital.setHospitalName(hospitalRequest.hospitalName());
        hospital.setFoundedAt(hospitalRequest.foundedAt());
        hospital.setAddress(address);
        return hospital;
    }


}
