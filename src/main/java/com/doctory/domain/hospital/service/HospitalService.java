package com.doctory.domain.hospital.service;


import com.doctory.domain.ResponseModel;
import com.doctory.domain.hospital.dto.HospitalDto;
import com.doctory.domain.hospital.dto.HospitalSearchDto;
import com.doctory.web.request.HospitalRequest;
import java.util.List;

public interface HospitalService {
    ResponseModel addNewHospital(HospitalRequest hospitalRequest);

    List<HospitalSearchDto> searchHospital(String hospitalName);

    HospitalDto getHospitalInfo(Long id);
    HospitalDto updateHospitalInfo(Long id,HospitalRequest hospitalRequest);

    List<HospitalDto> getAllHospital(Integer pageNo, Integer pageSize);
}
