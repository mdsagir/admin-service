package com.doctory.domain.hospital.service;

import com.doctory.common.DataNotFoundException;
import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.ResponseModel;
import com.doctory.domain.hospital.dto.HospitalDto;
import com.doctory.domain.hospital.dto.HospitalSearchDto;
import com.doctory.domain.hospital.mapper.HospitalMapper;
import com.doctory.infra.entity.Hospital;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.HospitalRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageHospital implements HospitalService {

    private static final Logger log = LoggerFactory.getLogger(ManageHospital.class);
    private final HospitalRepo hospitalRepo;
    private final HospitalMapper hospitalMapper;

    public ManageHospital(HospitalRepo hospitalRepo, HospitalMapper hospitalMapper) {
        this.hospitalRepo = hospitalRepo;
        this.hospitalMapper = hospitalMapper;
    }

    @Override
    public ResponseModel addNewHospital(HospitalRequest hospitalRequest) {
        String hospitalName = hospitalRequest.hospitalName();
        try {
            var hospital = hospitalMapper.toHospitalEntity(hospitalRequest);
            hospitalRepo.save(hospital);
            log.info("Hospital name is saved {}", hospitalName);
            return ResponseModel.of("Hospital added successfully");
        } catch (Exception exception) {
            log.error("Error while adding new hospital {}", exception.toString());
            throw new SomethingWentWrong("Unable to save the hospital");
        }
    }

    @Override
    public HospitalDto getHospitalInfo(Long id) {
        var hospital = findById(id);
        return hospitalMapper.toHospitalDto(hospital);
    }

    @Override
    public ResponseModel updateHospitalInfo(Long id, HospitalRequest hospitalRequest) {
        try {
            var hospital = findById(id);
            var hospitalEntity = hospitalMapper.toUpdateHospitalEntity(hospitalRequest, hospital);
            hospitalRepo.save(hospitalEntity);
            log.info("Update successfully hostel info for given id {}", id);
            return ResponseModel.of("Hospital updated successfully");
        } catch (Exception exception) {
            log.error("Error while updating hospital {}", exception.toString());
            throw new SomethingWentWrong("Unable to update the hospital");
        }
    }

    @Override
    public List<HospitalSearchDto> searchHospital(String hospitalName) {
        var pageRequest = PageRequest.of(0, 50);
        return hospitalRepo.searchByHospitalNameContaining(hospitalName, pageRequest);
    }


    @Override
    public List<HospitalDto> getAllHospital(Integer pageNo, Integer pageSize) {
        var page = pageSize == 0 ? 10 : pageSize;
        var pageRequest = PageRequest.of(pageNo, page);
        var allHospital = hospitalRepo.getAllHospital(pageRequest);
        return allHospital.getContent();
    }

    /**
     * {@code findById} used JPA repository to find {@link Hospital} database entity
     *
     * @param id entity id
     * @return {@link Hospital} database entity
     */
    private Hospital findById(Long id) {
        return hospitalRepo.getHospitalById(id).orElseThrow(() -> {
            log.error("Unable to find any hospital for given {}", id);
            return new DataNotFoundException(id + " is not found");
        });
    }
}
