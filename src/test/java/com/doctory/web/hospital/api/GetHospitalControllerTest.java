package com.doctory.web.hospital.api;

import com.doctory.common.DataNotFoundException;
import com.doctory.domain.ResponseModel;
import com.doctory.domain.hospital.service.HospitalService;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.rest.HospitalController;
import com.doctory.web.validator.AddHospitalValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;

import static com.doctory.domain.ResponseModel.of;
import static com.doctory.domain.hospital.dto.HospitalDto.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HospitalController.class)
@Import(AddHospitalValidator.class)
@MockBean(HospitalRepo.class)
class GetHospitalControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HospitalService hospitalService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_find_hospital_info_by_id_with_success_response_status_200() throws Exception {
        Long id = 100L;
        var hospitalDto = of(id, "AK Hospital", "1989", "addressLine1", "addressLine2", "854633", "Bihar", "India", LocalDateTime.now(), LocalDateTime.now());
        var jsonResponseDto = objectMapper.writeValueAsString(hospitalDto);


        given(hospitalService.getHospitalInfo(100L)).willReturn(hospitalDto);
        var mvcResult = mockMvc.perform(get("/api/hospital").contentType(APPLICATION_JSON).param("id", String.valueOf(id))).andExpect(status().is2xxSuccessful()).andReturn();
        var jsonResponse = mvcResult.getResponse().getContentAsString();
        assertThat(jsonResponse).isEqualTo(jsonResponseDto);

    }

    @Test
    void test_find_hospital_info_by_id_validation_failed_with_error_response_status_400() throws Exception {

        Long id = 100L;
        var hospitalDto = of(id, "AK Hospital", "1989", "addressLine1", "addressLine2", "854633", "Bihar", "India", LocalDateTime.now(), LocalDateTime.now());

        given(hospitalService.getHospitalInfo(100L)).willReturn(hospitalDto);
        var mvcResult = mockMvc.perform(get("/api/hospital").contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
        var jsonResponse = mvcResult.getResponse().getContentAsString();
        assertThat(jsonResponse).isEqualTo(objectMapper.writeValueAsString(hospitalDto));

    }

    @Test
    void test_not_find_hospital_info_by_id_with_error_response_status_404() throws Exception {

        Long id = 100L;
        ResponseModel responseModel = of(id + " is not found");
        given(hospitalService.getHospitalInfo(100L)).willThrow(new DataNotFoundException(id + " is not found"));
        var mvcResult = mockMvc.perform(get("/api/hospital").contentType(APPLICATION_JSON).
                param("id", String.valueOf(id))).andExpect(status().isNotFound()).andReturn();
        var jsonResponse = mvcResult.getResponse().getContentAsString();
        assertThat(jsonResponse).isEqualTo(objectMapper.writeValueAsString(responseModel));

    }
    @Test
    void test_find_hospital_info_by_id_runtime_exception_failed_with_error_response_status_404() throws Exception {
        ResponseModel responseModel = of("Unable to process the request at this time");
        Long id = 100L;

        given(hospitalService.getHospitalInfo(100L)).willThrow(NullPointerException.class);
        var mvcResult = mockMvc.perform(get("/api/hospital").contentType(APPLICATION_JSON).
                param("id", String.valueOf(id))).andExpect(status().isInternalServerError()).andReturn();
        var jsonResponse = mvcResult.getResponse().getContentAsString();
        assertThat(jsonResponse).isEqualTo(objectMapper.writeValueAsString(responseModel));

    }
}
