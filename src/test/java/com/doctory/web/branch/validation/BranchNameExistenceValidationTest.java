package com.doctory.web.branch.validation;

import com.doctory.infra.entity.Branch;
import com.doctory.infra.repo.BranchRepo;
import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.BranchRequest;
import com.doctory.web.request.HospitalRequest;
import com.doctory.web.validator.AddBranchValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class BranchNameExistenceValidationTest {

    @Autowired
    private AddBranchValidator addBranchValidator;
    @MockBean
    private BranchRepo branchRepo;

    @Test
    void test_hospital_name_exist_then_validation_failed() {
        String branchName = "Port luis";
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");

        var branchRequest = new BranchRequest(1L, branchName, addressRequest);
        var branch = new Branch();
        branch.setBranchName(branchName);
        given(branchRepo.findByBranchName(branchRequest.branchName())).willReturn(Optional.of(branch));
        Errors errors = new BeanPropertyBindingResult(branchRequest, "");
        addBranchValidator.validate(branchRequest, errors);
        assertThat(errors.hasErrors()).isTrue();
        var fieldError = errors.getFieldError();
        assertThat(fieldError).isNotNull();
        var errorMessage = errors.getFieldError().getDefaultMessage();
        assertThat(errorMessage).isEqualTo(branchName + " branch name already exist");
    }

    @Test
    void when_branch_name_not_exist_then_validation_success() {
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");

        var branchRequest = new BranchRequest(1L,"Port luis",  addressRequest);
        given(branchRepo.findByBranchName(branchRequest.branchName())).willReturn(Optional.empty());
        Errors errors = new BeanPropertyBindingResult(branchRequest, "");
        addBranchValidator.validate(branchRequest, errors);
        assertThat(errors.hasErrors()).isFalse();
    }
}
