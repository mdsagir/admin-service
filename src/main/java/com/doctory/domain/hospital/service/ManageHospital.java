package com.doctory.domain.hospital.service;

import com.doctory.domain.ResponseModel;
import com.doctory.domain.hospital.mapper.HospitalMapper;
import com.doctory.infra.entity.Hospital;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.AddHospital;
import org.springframework.stereotype.Service;

@Service
public class ManageHospital implements HospitalService{

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
     * @param addHospital parameter {@link AddHospital} that convert to entity model and persist to database.
     * @return return created {@link AddHospital} to response back to API.
     */
    @Override
    public ResponseModel addNewHospital(AddHospital addHospital) {

        Hospital hospital = hospitalMapper.toHospitalEntity(addHospital);
        hospitalRepo.save(hospital);
        return ResponseModel.of("Hospital added successfully");

    }
}
