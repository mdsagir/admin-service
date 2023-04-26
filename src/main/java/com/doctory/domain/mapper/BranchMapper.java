package com.doctory.domain.mapper;

import com.doctory.infra.entity.Branch;
import com.doctory.web.request.BranchRequest;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {

    private final CommonMapper commonMapper;

    public BranchMapper(CommonMapper commonMapper) {
        this.commonMapper = commonMapper;
    }

    public Branch toEntity(BranchRequest branchRequest) {
        var branchName = branchRequest.branchName();
        Branch branch = new Branch();
        branch.setBranchName(branchName);

        var addressRequest = branchRequest.addressRequest();
        var addressEntity = commonMapper.toAddressEntity(addressRequest);
        branch.setAddress(addressEntity);
        return branch;
    }

    public void toUpdateBranchEntity(BranchRequest branchRequest, Branch branch) {
        var addressRequest = branchRequest.addressRequest();
        var address = branch.getAddress();
        commonMapper.updateAddressEntity(addressRequest,address);
        branch.setBranchName(branchRequest.branchName());


    }
}
