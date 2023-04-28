package com.doctory.web.validator;

import com.doctory.infra.repo.BranchRepo;
import com.doctory.web.request.BranchRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddBranchValidator implements Validator {

    private final BranchRepo branchRepo;

    public AddBranchValidator(BranchRepo branchRepo) {
        this.branchRepo = branchRepo;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return BranchRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object object,@NonNull Errors errors) {
        var branchRequest = (BranchRequest) object;
        var branchName = branchRequest.branchName();
        var branchOptional = branchRepo.findByBranchName(branchName);
        branchOptional.ifPresent(branch -> errors.rejectValue("branchName", "branchName.already", branch.getBranchName() + " branch name already exist"));
    }
}
