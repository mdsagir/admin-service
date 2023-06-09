package com.doctory.domain.branch.dto;


import com.doctory.infra.entity.Branch;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record BranchDto(Long id, String branchName, String addressLine1, String addressLine2, String pinCode,
                        String state, String country,
                        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime created,
                        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime modified) {

    public BranchDto(Branch branch) {
        this(branch.getId(), branch.getBranchName(), branch.getAddress().getAddressLine1(), branch.getAddress().getAddressLine2(), branch.getAddress().getPinCode(), branch.getAddress().getState(), branch.getAddress().getCountry(), branch.getCommon().getCreatedDate(), branch.getCommon().getModifiedDate());
    }

    public static BranchDto of(Branch branch) {
        var address = branch.getAddress();
        var common = branch.getCommon();
        return new BranchDto(branch.getId(), branch.getBranchName(), address.getAddressLine1(), address.getAddressLine2(), address.getPinCode(), address.getState(), address.getCountry(), common.getCreatedDate(), common.getModifiedDate());
    }
}
