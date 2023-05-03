package com.doctory.domain.branch.service;

import com.doctory.domain.ResponseModel;
import com.doctory.domain.SearchDto;
import com.doctory.domain.branch.dto.BranchDto;
import com.doctory.web.request.BranchRequest;
import com.doctory.web.request.UpdateBranchRequest;

import java.util.List;

public interface BranchService {

    ResponseModel addNewBranch(BranchRequest branchRequest);
    BranchDto getBranchInfo(Long id);

    ResponseModel updateBranch(UpdateBranchRequest updateBranchRequest);

    List<SearchDto> searchBranch(String searchText);

    List<BranchDto> getAllBranch(Integer pageNo, Integer pageSize);
}
