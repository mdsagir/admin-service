package com.doctory.domain.doctor.service;

import com.doctory.common.DataNotFoundException;
import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.ResponseModel;
import com.doctory.domain.SearchDto;
import com.doctory.domain.doctor.dto.DoctorDto;
import com.doctory.domain.mapper.DoctorMapper;
import com.doctory.infra.entity.Doctor;
import com.doctory.infra.repo.BranchRepo;
import com.doctory.infra.repo.DoctorRepo;
import com.doctory.web.request.DoctorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class DoctorManager implements DoctorService {

    private static final Logger log = LoggerFactory.getLogger(DoctorManager.class);
    private final DoctorRepo doctorRepo;
    private final DoctorMapper doctorMapper;
    private final BranchRepo branchRepo;

    public DoctorManager(DoctorRepo doctorRepo, DoctorMapper doctorMapper, BranchRepo branchRepo) {
        this.doctorRepo = doctorRepo;
        this.doctorMapper = doctorMapper;
        this.branchRepo = branchRepo;
    }


    @Override
    public ResponseModel addNewDoctor(DoctorRequest doctorRequest) {
        var doctorName = doctorRequest.personRequest().firstName();
        try {
            var doctorEntity = doctorMapper.toDoctorEntity(doctorRequest);
            doctorRepo.save(doctorEntity);
            log.info("Doctor is saved {}", doctorName);
            return ResponseModel.of("Doctor added successfully");
        } catch (Exception exception) {
            log.error("Error while adding new doctor {}", exception.toString());
            throw new SomethingWentWrong("Unable to save the doctor");
        }
    }

    @Override
    public List<SearchDto> searchDoctor(String text) {
        var pageRequest = of(0, 50);
        return doctorRepo.searchByDoctorNameContaining(text, pageRequest);
    }

    @Override
    public DoctorDto getDoctorInfo(Long id) {
        var doctor = findById(id);
        return DoctorDto.of(doctor);
    }

    @Override
    public ResponseModel updateDoctorInfo(Long id, Long branchId, DoctorRequest doctorRequest) {
        try {
            var doctor = findById(id);
            doctorMapper.toUpdateDoctorEntity(doctorRequest, doctor);
            var branch = branchRepo.getReferenceById(branchId);
            doctor.setBranch(branch);
            doctorRepo.save(doctor);
            log.info("Update successfully doctor info for given doctor id  {} and branch id {}", id,branchId);
            return ResponseModel.of("Doctor updated successfully");
        } catch (Exception exception) {
            log.error("Error while updating doctor {}", exception.toString());
            throw new SomethingWentWrong("Unable to update the doctor info");
        }
    }

    @Override
    public List<DoctorDto> getAllDoctor(Integer pageNo, Integer pageSize) {
        var page = pageSize == 0 ? 10 : pageSize;
        var pageRequest = PageRequest.of(pageNo, page);
        var allDoctor = doctorRepo.getAllDoctor(pageRequest);
        return allDoctor.getContent();
    }

    /**
     * {@code findById} used JPA repository to find {@link Doctor} database entity
     *
     * @param id entity id
     * @return {@link Doctor} database entity
     */
    private Doctor findById(Long id) {
        return doctorRepo.getDoctorById(id).orElseThrow(() -> {
            log.error("Unable to find any hospital for given {}", id);
            return new DataNotFoundException(id + " is not found");
        });
    }
}
