package com.doctory.web.branch.api;

import com.doctory.common.DataNotFoundException;
import com.doctory.domain.ResponseModel;
import com.doctory.domain.branch.dto.BranchDto;
import com.doctory.domain.branch.service.BranchService;
import com.doctory.infra.repo.BranchRepo;
import com.doctory.web.rest.BranchController;
import com.doctory.web.validator.AddBranchValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Map;

import static com.doctory.domain.ResponseModel.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BranchController.class)
@Import(AddBranchValidator.class)
@MockBean(BranchRepo.class)
class GetBranchAPITest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BranchService branchService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void when_find_branch_info_by_id_with_success_response_status_200() throws Exception {
        Long id = 100L;

        var branchDto = new BranchDto(id, "Port Luis", "addressLine1", "addressLine2", "854633", "Bihar", "India", LocalDateTime.now(), LocalDateTime.now());

        var jsonResponseDto = objectMapper.writeValueAsString(branchDto);

        given(branchService.getBranchInfo(100L)).willReturn(branchDto);
        var mvcResult = mockMvc.perform(get("/api/branch").contentType(APPLICATION_JSON).param("id", String.valueOf(id)))
                .andExpect(status().isOk()).andReturn();
        var jsonResponse = mvcResult.getResponse().getContentAsString();
        assertThat(jsonResponse).isEqualTo(jsonResponseDto);

    }

    @Test
    void when_find_branch_info_by_id_validation_failed_with_error_response_status_400() throws Exception {
        var error = Map.of("error", "The id must be defined");

        Long id = 100L;

        var branchDto = new BranchDto(id, "Port Luis", "addressLine1", "addressLine2", "854633", "Bihar", "India", LocalDateTime.now(), LocalDateTime.now());

        given(branchService.getBranchInfo(100L)).willReturn(branchDto);
        var mvcResult = mockMvc.perform(get("/api/branch").contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
        var jsonResponse = mvcResult.getResponse().getContentAsString();
        assertThat(jsonResponse).isEqualTo(objectMapper.writeValueAsString(error));

    }

    @Test
    void when_not_find_branch_info_by_id_with_error_response_status_404() throws Exception {

        Long id = 100L;
        ResponseModel responseModel = of(id + " is not found");
        given(branchService.getBranchInfo(100L)).willThrow(new DataNotFoundException(id + " is not found"));
        var mvcResult = mockMvc.perform(get("/api/branch").contentType(APPLICATION_JSON).
                param("id", String.valueOf(id))).andExpect(status().isNotFound()).andReturn();
        var jsonResponse = mvcResult.getResponse().getContentAsString();
        assertThat(jsonResponse).isEqualTo(objectMapper.writeValueAsString(responseModel));

    }

    @Test
    void when_find_hospital_info_by_id_runtime_exception_failed_with_error_response_status_404() throws Exception {
        ResponseModel responseModel = of("Unable to process the request at this time");
        Long id = 100L;

        given(branchService.getBranchInfo(100L)).willThrow(NullPointerException.class);
        var mvcResult = mockMvc.perform(get("/api/branch").contentType(APPLICATION_JSON).
                param("id", String.valueOf(id))).andExpect(status().isInternalServerError()).andReturn();
        var jsonResponse = mvcResult.getResponse().getContentAsString();
        assertThat(jsonResponse).isEqualTo(objectMapper.writeValueAsString(responseModel));

    }
}
