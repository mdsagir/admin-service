package com.doctory.domain.branch.service;

import com.doctory.domain.ResponseModel;
import com.doctory.domain.SearchDto;
import com.doctory.domain.branch.dto.BranchDto;
import com.doctory.web.request.BranchRequest;

import java.util.List;

public interface BranchService {

    ResponseModel addNewBranch(BranchRequest branchRequest);
    BranchDto getBranchInfo(Long id);

    ResponseModel updateBranch(Long id, BranchRequest branchRequest);

    List<SearchDto> searchBranch(String searchText);

    List<BranchDto> getAllBranch(Integer pageNo, Integer pageSize);
}
