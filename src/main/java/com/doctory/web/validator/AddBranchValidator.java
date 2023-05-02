package com.doctory.web.validator;

import com.doctory.infra.repo.BranchRepo;
import com.doctory.web.request.BranchRequest;
import com.doctory.web.request.UpdateBranchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddBranchValidator implements Validator {

    private static final Logger log = LoggerFactory.getLogger(AddBranchValidator.class);
    private final BranchRepo branchRepo;

    public AddBranchValidator(BranchRepo branchRepo) {
        this.branchRepo = branchRepo;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return BranchRequest.class.isAssignableFrom(clazz) || UpdateBranchRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object object, @NonNull Errors errors) {
        if (object instanceof BranchRequest branchRequest) {
            var branchName = branchRequest.branchName();
            var branchOptional = branchRepo.findByBranchName(branchName);
            branchOptional.ifPresent(branch -> {
                log.error("branch name already exist in data base");
                errors.rejectValue("branchName", "branchName.already", branch.getBranchName() + " branch name already exist");
            });
        }
    }
}
