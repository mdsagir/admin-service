package com.doctory.domain.hospital.service;

import com.doctory.common.DataNotFoundException;
import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.ResponseModel;
import com.doctory.domain.hospital.dto.HospitalDto;
import com.doctory.domain.SearchDto;
import com.doctory.domain.mapper.HospitalMapper;
import com.doctory.infra.entity.Hospital;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.HospitalRequest;
import com.doctory.web.request.UpdateHospitalRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.doctory.domain.ResponseModel.of;
import static org.springframework.data.domain.PageRequest.of;

@Service
public class HospitalManager implements HospitalService {

    private static final Logger log = LoggerFactory.getLogger(HospitalManager.class);
    private final HospitalRepo hospitalRepo;
    private final HospitalMapper hospitalMapper;

    public HospitalManager(HospitalRepo hospitalRepo, HospitalMapper hospitalMapper) {
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
            return of("Hospital added successfully");
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
    public ResponseModel updateHospitalInfo(UpdateHospitalRequest updateHospitalRequest) {
        try {
            var id = updateHospitalRequest.id();
            var hospital = findById(id);
            var hospitalEntity = hospitalMapper.toUpdateHospitalEntity(updateHospitalRequest, hospital);
            hospitalRepo.save(hospitalEntity);
            log.info("Update successfully hostel info for given id {}", id);
            return of("Hospital updated successfully");
        } catch (Exception exception) {
            log.error("Error while updating hospital {}", exception.toString());
            throw new SomethingWentWrong("Unable to update the hospital");
        }
    }

    @Override
    public List<SearchDto> searchHospital(String hospitalName) {
        try {
            var pageRequest = of(0, 50);
            return hospitalRepo.searchByHospitalNameContaining(hospitalName, pageRequest);
        } catch (Exception exception) {
            log.error("Error while search hospital {}", exception.toString());
            throw new SomethingWentWrong("Unable to search the hospital");
        }

    }


    @Override
    public List<HospitalDto> getAllHospital(Integer pageNo, Integer pageSize) {
        try {

            var page = pageSize == 0 ? 10 : pageSize;
            var pageRequest = of(pageNo, page);
            var allHospital = hospitalRepo.getAllHospital(pageRequest);
            return allHospital.getContent();
        } catch (Exception exception) {
            log.error("Error while find hospital {}", exception.toString());
            throw new SomethingWentWrong("Unable to find the hospital");
        }
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
