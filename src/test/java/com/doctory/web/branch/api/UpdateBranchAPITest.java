package com.doctory.web.branch.api;

import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.ResponseModel;
import com.doctory.domain.branch.service.BranchService;
import com.doctory.infra.repo.BranchRepo;
import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.UpdateBranchRequest;
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

import java.util.Optional;

import static com.doctory.domain.ResponseModel.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BranchController.class)
@Import(AddBranchValidator.class)
@MockBean(BranchRepo.class)
class UpdateBranchAPITest {

    public static final String URL = "/api/branch";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BranchService branchService;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BranchRepo branchRepo;

    @Test
    void when_update_branch_with_success_validation_then_return_200_status() throws Exception {

        var responseModel = new ResponseModel("Branch updated successfully");
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var updateHospitalRequest = new UpdateBranchRequest(101L, 102L, "Port luis", addressRequest);
        given(branchRepo.findByBranchName(updateHospitalRequest.branchName())).willReturn(Optional.empty());
        given(branchService.updateBranch(updateHospitalRequest)).willReturn(responseModel);
        var mvcResult = mockMvc.perform(put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateHospitalRequest)))
                .andExpect(status().isOk())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }

    @Test
    void when_update_branch_with_runtime_exception_then_return_500_status() throws Exception {
        ResponseModel responseModel = of("Unable to process the request at this time");
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var updateBranchRequest = new UpdateBranchRequest(101L, 10L, "1989", addressRequest);
        given(branchRepo.findByBranchName(updateBranchRequest.branchName())).willReturn(Optional.empty());
        given(branchService.updateBranch(updateBranchRequest)).willThrow(NullPointerException.class);
        var mvcResult = mockMvc.perform(put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBranchRequest)))
                .andExpect(status().isInternalServerError())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }

    @Test
    void when_update_branch_with_something_went_wrong_exception_then_return_400_status() throws Exception {
        String errorMessage = "Unable to update the hospital";
        ResponseModel responseModel = of(errorMessage);
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var updateBranchRequest = new UpdateBranchRequest(101L, 123L, "1989", addressRequest);
        given(branchRepo.findByBranchName(updateBranchRequest.branchName())).willReturn(Optional.empty());
        var somethingWentWrong = new SomethingWentWrong(errorMessage);
        given(branchService.updateBranch(updateBranchRequest)).willThrow(somethingWentWrong);
        var mvcResult = mockMvc.perform(put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBranchRequest)))
                .andExpect(status().isInternalServerError())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }
}
