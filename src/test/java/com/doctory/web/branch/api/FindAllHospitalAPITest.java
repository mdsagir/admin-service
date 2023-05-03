package com.doctory.web.branch.api;

import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.ResponseModel;
import com.doctory.domain.branch.dto.BranchDto;
import com.doctory.domain.branch.service.BranchService;
import com.doctory.infra.entity.Address;
import com.doctory.infra.entity.Branch;
import com.doctory.infra.entity.Common;
import com.doctory.infra.repo.BranchRepo;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BranchController.class)
@Import(AddBranchValidator.class)
@MockBean(BranchRepo.class)
class FindAllHospitalAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BranchService branchService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void when_find_all_branch_with_success_validation_then_return_200_status() throws Exception {

        int pageNo = 0;
        int pageSize = 0;

        Branch branch = new Branch();
        branch.setAddress(new Address());
        branch.setCommon(new Common());
        var branchDto = List.of(BranchDto.of(branch));
        given(branchService.getAllBranch(pageNo, pageSize)).willReturn(branchDto);
        var mvcResult = mockMvc.perform(get("/api/branch/all")
                        .contentType(MediaType.APPLICATION_JSON).
                        param("pageNo", String.valueOf(pageNo)).
                        param("pageSize", String.valueOf(pageNo)))
                .andExpect(status().isOk()).andReturn();
        var contentAsString = mvcResult.getResponse()
                .getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(branchDto));
    }

    @Test
    void when_find_all_branch_with_runtime_exception_then_return_400_status() throws Exception {
        ResponseModel responseModel = ResponseModel.of("Unable to process the request at this time");
        int pageNo = 0;
        int pageSize = 0;
        given(branchService.getAllBranch(pageNo, pageSize)).willThrow(NullPointerException.class);
        var mvcResult = mockMvc.perform(get("/api/branch/all")
                        .contentType(MediaType.APPLICATION_JSON).
                        param("pageNo", String.valueOf(pageNo)).
                        param("pageSize", String.valueOf(pageNo)))
                .andExpect(status().isInternalServerError()).andReturn();
        var contentAsString = mvcResult.getResponse()
                .getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }

    @Test
    void when_find_all_branch_with_something_went_wrong_exception_then_return_400_status() throws Exception {
        String errorMessage = "Unable to find the branch";
        ResponseModel responseModel = ResponseModel.of(errorMessage);
        var somethingWentWrong = new SomethingWentWrong(errorMessage);
        int pageNo = 0;
        int pageSize = 0;
        given(branchService.getAllBranch(pageNo, pageSize)).willThrow(somethingWentWrong);
        var mvcResult = mockMvc.perform(get("/api/branch/all")
                        .contentType(MediaType.APPLICATION_JSON).
                        param("pageNo", String.valueOf(pageNo)).
                        param("pageSize", String.valueOf(pageNo)))
                .andExpect(status().isInternalServerError()).andReturn();
        var contentAsString = mvcResult.getResponse()
                .getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }
}
