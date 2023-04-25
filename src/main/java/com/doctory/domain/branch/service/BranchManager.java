package com.doctory.domain.branch.service;

import com.doctory.domain.ResponseModel;
import com.doctory.domain.mapper.BranchMapper;
import com.doctory.infra.repo.BranchRepo;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.BranchRequest;
import org.springframework.stereotype.Service;

@Service
public class BranchManager implements BranchService {

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
        var hospitalId = branchRequest.hospitalId();
        var hospitalRepoReference = hospitalRepo.getReferenceById(hospitalId);
        var branch = branchMapper.toEntity(branchRequest);
        branch.setHospital(hospitalRepoReference);
        branchRepo.save(branch);


        return null;
    }
}
