package com.doctory.web.branch.api;

import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.ResponseModel;
import com.doctory.domain.branch.service.BranchService;
import com.doctory.infra.entity.Branch;
import com.doctory.infra.entity.Hospital;
import com.doctory.infra.repo.BranchRepo;
import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.BranchRequest;
import com.doctory.web.request.HospitalRequest;
import com.doctory.web.rest.BranchController;
import com.doctory.web.validator.AddBranchValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Optional;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BranchController.class)
@Import(AddBranchValidator.class)
class CreateBranchAPITest {

    @MockBean
    private BranchService branchService;
    @MockBean
    private BranchRepo branchRepo;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void when_create_new_branch_with_success_validation_then_return_201_status() throws Exception {

        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var branchRequest = new BranchRequest(1L, "Port luis", addressRequest);
        given(branchRepo.findByBranchName(branchRequest.branchName())).willReturn(Optional.empty());
        var responseModel = new ResponseModel("Branch added successfully");
        given(branchService.addNewBranch(branchRequest)).willReturn(responseModel);
        var mvcResult = mockMvc.perform(post("/api/branch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branchRequest)))
                .andExpect(status().isCreated())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }

    @Test
    void when_create_new_branch_with_failed_validation_then_return_400_status() throws Exception {


        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var branchRequest = new BranchRequest(1L, "Port luis", addressRequest);
        Branch branch = new Branch();
        branch.setBranchName(branchRequest.branchName());
        given(branchRepo.findByBranchName(branchRequest.branchName())).willReturn(of(branch));
        var mvcResult = mockMvc.perform(post("/api/branch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branchRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        var errors = Map.of("branchName", branchRequest.branchName() + " branch name already exist");
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(errors));
    }

    @Test
    void when_create_new_branch_with_runtime_exception_then_return_500_status() throws Exception {
        ResponseModel responseModel = ResponseModel.of("Unable to process the request at this time");
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var branchRequest = new BranchRequest(1L, "Port luis", addressRequest);
        given(branchRepo.findByBranchName(branchRequest.branchName())).willReturn(Optional.empty());
        given(branchService.addNewBranch(branchRequest)).willThrow(NullPointerException.class);
        var mvcResult = mockMvc.perform(post("/api/branch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branchRequest)))
                .andExpect(status().isInternalServerError())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }

    @Test
    void test_create_new_branch_with_something_went_wrong_exception_then_return_400_status() throws Exception {
        String errorMessage = "Unable to save the branch";
        ResponseModel responseModel = ResponseModel.of(errorMessage);
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var branchRequest = new BranchRequest(1L, "Port luis", addressRequest);
        given(branchRepo.findByBranchName(branchRequest.branchName())).willReturn(Optional.empty());
        var somethingWentWrong = new SomethingWentWrong(errorMessage);
        given(branchService.addNewBranch(branchRequest)).willThrow(somethingWentWrong);
        var mvcResult = mockMvc.perform(post("/api/branch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branchRequest)))
                .andExpect(status().isInternalServerError())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }

}
