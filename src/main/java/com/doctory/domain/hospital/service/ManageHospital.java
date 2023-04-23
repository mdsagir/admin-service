package com.doctory.domain.hospital.service;

import com.doctory.common.DataNotFoundException;
import com.doctory.domain.ResponseModel;
import com.doctory.domain.hospital.dto.HospitalDto;
import com.doctory.domain.hospital.dto.HospitalSearchDto;
import com.doctory.domain.hospital.mapper.HospitalMapper;
import com.doctory.infra.entity.Hospital;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.HospitalRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ManageHospital implements HospitalService {

    private final HospitalRepo hospitalRepo;
    private final HospitalMapper hospitalMapper;

    public ManageHospital(HospitalRepo hospitalRepo, HospitalMapper hospitalMapper) {
        this.hospitalRepo = hospitalRepo;
        this.hospitalMapper = hospitalMapper;
    }

    /**
     * <p>
     * A new Hospital persist to database then create DTO to send back response
     *
     * @param hospitalRequest parameter {@link HospitalRequest} that convert to entity model and persist to database.
     * @return return created {@link HospitalRequest} to response back to API.
     */
    @Override
    public ResponseModel addNewHospital(HospitalRequest hospitalRequest) {

        Hospital hospital = hospitalMapper.toHospitalEntity(hospitalRequest);
        hospitalRepo.save(hospital);
        return ResponseModel.of("Hospital added successfully");

    }

    @Override
    public HospitalDto getHospitalInfo(Long id) {
        Hospital hospital = findById(id);
        return hospitalMapper.toHospitalDto(hospital);
    }

    @Override
    public HospitalDto updateHospitalInfo(Long id, HospitalRequest hospitalRequest) {
        Hospital hospital = findById(id);
        Hospital hospitalEntity = hospitalMapper.toUpdateHospitalEntity(hospitalRequest, hospital);
        Hospital updatedHospital = hospitalRepo.save(hospitalEntity);
        return hospitalMapper.toHospitalDto(updatedHospital);
    }

    @Override
    public List<HospitalSearchDto> searchHospital(String hospitalName) {
        PageRequest pageRequest = PageRequest.of(0, 50);
        return hospitalRepo.searchByHospitalNameContaining(hospitalName, pageRequest);
    }


    @Override
    public List<HospitalDto> getAllHospital(Integer pageNo, Integer pageSize) {
        int page = pageSize == 0 ? 2 : pageSize;
        PageRequest pageRequest = PageRequest.of(pageNo, page);
        Page<HospitalDto> allHospital = hospitalRepo.getAllHospital(pageRequest);
        return allHospital.getContent();
    }

    private Hospital findById(Long id) {
        return hospitalRepo.getHospitalById(id).orElseThrow(() -> new DataNotFoundException(id+" is not found"));
    }
}
