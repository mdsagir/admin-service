package com.doctory.domain.branch.service;

import com.doctory.domain.ResponseModel;
import com.doctory.domain.branch.dto.BranchDto;
import com.doctory.web.request.BranchRequest;

public interface BranchService {

    ResponseModel addNewBranch(BranchRequest branchRequest);
    BranchDto getBranchInfo(Long id);
}
