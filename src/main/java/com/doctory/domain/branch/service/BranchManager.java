package com.doctory.domain.branch.service;

import com.doctory.common.DataNotFoundException;
import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.ResponseModel;
import com.doctory.domain.SearchDto;
import com.doctory.domain.branch.dto.BranchDto;
import com.doctory.domain.mapper.BranchMapper;
import com.doctory.infra.entity.Branch;
import com.doctory.infra.repo.BranchRepo;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.BranchRequest;
import com.doctory.web.request.UpdateBranchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.doctory.domain.branch.dto.BranchDto.of;

@Service
public class BranchManager implements BranchService {

    private static final Logger log = LoggerFactory.getLogger(BranchManager.class);

    private final BranchRepo branchRepo;
    private final HospitalRepo hospitalRepo;
    private final BranchMapper branchMapper;

    public BranchManager(BranchRepo branchRepo, HospitalRepo hospitalRepo, BranchMapper branchMapper) {
        this.branchRepo = branchRepo;
        this.hospitalRepo = hospitalRepo;
        this.branchMapper = branchMapper;
    }

    @Override
    public ResponseModel addNewBranch(BranchRequest branchRequest) {
        try {

            var hospitalId = branchRequest.hospitalId();
            var hospitalRepoReference = hospitalRepo.getReferenceById(hospitalId);
            var branch = branchMapper.toEntity(branchRequest);
            branch.setHospital(hospitalRepoReference);
            branchRepo.save(branch);
            log.info("Branch is saved {}", branch.getId());
            return ResponseModel.of("Branch added successfully");
        } catch (Exception exception) {
            log.error("Error while adding new branch {}", exception.toString());
            throw new SomethingWentWrong("Unable to save the branch");
        }
    }

    @Override
    public BranchDto getBranchInfo(Long id) {
        var branch = findById(id);
        return of(branch);
    }

    @Override
    public ResponseModel updateBranch(UpdateBranchRequest updateBranchRequest) {
        var branchId = updateBranchRequest.branchId();
        var branch = findById(branchId);
        try {
            branchMapper.toUpdateBranchEntity(updateBranchRequest, branch);
            branchRepo.save(branch);
            log.info("Branch is updated {}", branch.getId());
            return ResponseModel.of("Branch updated successfully");
        } catch (Exception exception) {
            log.error("Error while updating hospital {}", exception.toString());
            throw new SomethingWentWrong("Unable to update the branch");
        }
    }

    @Override
    public List<SearchDto> searchBranch(String searchText) {
        try {
            var pageRequest = PageRequest.of(0, 50);
            return branchRepo.searchByBranchNameContaining(searchText, pageRequest);
        } catch (Exception exception) {
            log.error("Error while search hospital {}", exception.toString());
            throw new SomethingWentWrong("Unable to search the branch");
        }
    }

    @Override
    public List<BranchDto> getAllBranch(Integer pageNo, Integer pageSize) {
        try {
            var page = pageSize == 0 ? 10 : pageSize;
            var pageRequest = PageRequest.of(pageNo, page);
            return branchRepo.getAllBranch(pageRequest).getContent();
        } catch (Exception exception) {
            log.error("Error while getting all branch {}", exception.toString());
            throw new SomethingWentWrong("Unable to get the branch");
        }
    }

    private Branch findById(Long id) {
        return branchRepo.findBranchById(id).orElseThrow(() -> {
            log.error("Unable to find any branch for given {}", id);
            return new DataNotFoundException(id + " is not found");
        });
    }
}
