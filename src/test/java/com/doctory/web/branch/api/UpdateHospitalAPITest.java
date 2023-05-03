package com.doctory.web.branch.api;

import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.ResponseModel;
import com.doctory.domain.hospital.service.HospitalService;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.UpdateHospitalRequest;
import com.doctory.web.rest.HospitalController;
import com.doctory.web.validator.AddHospitalValidator;
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

import static com.doctory.domain.ResponseModel.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HospitalController.class)
@Import(AddHospitalValidator.class)
@MockBean(HospitalRepo.class)
class UpdateHospitalAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HospitalService hospitalService;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HospitalRepo hospitalRepo;

    @Test
    void test_update_hospital_with_success_validation_then_return_200_status() throws Exception {

        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var updateHospitalRequest = new UpdateHospitalRequest(101L, "AK Hospital", "1989", addressRequest);
        given(hospitalRepo.findByHospitalName(updateHospitalRequest.hospitalName())).willReturn(Optional.empty());
        var responseModel = new ResponseModel("Hospital updated successfully");
        given(hospitalService.updateHospitalInfo(updateHospitalRequest)).willReturn(responseModel);
        var mvcResult = mockMvc.perform(put("/api/hospital")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateHospitalRequest)))
                .andExpect(status().isOk())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }

    @Test
    void test_update_hospital_with_failed_property_validation_then_return_400_status() throws Exception {

        var error = Map.of("hospitalName", "The hospital name must be defined");
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var updateHospitalRequest = new UpdateHospitalRequest(101L, null, "1989", addressRequest);
        given(hospitalRepo.findByHospitalName(updateHospitalRequest.hospitalName())).willReturn(Optional.empty());
        var responseModel = new ResponseModel("Hospital updated successfully");
        given(hospitalService.updateHospitalInfo(updateHospitalRequest)).willReturn(responseModel);
        var mvcResult = mockMvc.perform(put("/api/hospital")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateHospitalRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(error));
    }

    @Test
    void test_update_hospital_with_runtime_exception_then_return_500_status() throws Exception {
        ResponseModel responseModel = of("Unable to process the request at this time");
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var updateHospitalRequest = new UpdateHospitalRequest(101L, "AK Hospital", "1989", addressRequest);
        given(hospitalRepo.findByHospitalName(updateHospitalRequest.hospitalName())).willReturn(Optional.empty());
        given(hospitalService.updateHospitalInfo(updateHospitalRequest)).willThrow(NullPointerException.class);
        var mvcResult = mockMvc.perform(put("/api/hospital")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateHospitalRequest)))
                .andExpect(status().isInternalServerError())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }

    @Test
    void test_update_hospital_with_SomethingWentWrong_exception_then_return_400_status() throws Exception {
        String errorMessage = "Unable to update the hospital";
        ResponseModel responseModel = of(errorMessage);
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var updateHospitalRequest = new UpdateHospitalRequest(101L, "AK Hospital", "1989", addressRequest);
        given(hospitalRepo.findByHospitalName(updateHospitalRequest.hospitalName())).willReturn(Optional.empty());
        var somethingWentWrong = new SomethingWentWrong(errorMessage);
        given(hospitalService.updateHospitalInfo(updateHospitalRequest)).willThrow(somethingWentWrong);
        var mvcResult = mockMvc.perform(put("/api/hospital")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateHospitalRequest)))
                .andExpect(status().isInternalServerError())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }
}
