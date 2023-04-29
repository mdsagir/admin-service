package com.doctory.web.hospital.api;

import com.doctory.common.SomethingWentWrong;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static com.doctory.domain.hospital.dto.HospitalDto.of;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HospitalController.class)
@Import(AddHospitalValidator.class)
@MockBean(HospitalRepo.class)
class FindAllHospitalControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HospitalService hospitalService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_find_all_hospital_with_success_validation_then_return_200_status() throws Exception {

        int pageNo = 0;
        int pageSize = 0;
        var hospitalDto = of(of(10L, "AK Hospital", "1989", "addressLine1", "addressLine2", "854633", "Bihar", "India", LocalDateTime.now(), LocalDateTime.now()));
        given(hospitalService.getAllHospital(pageNo, pageSize)).willReturn(hospitalDto);
        var mvcResult = mockMvc.perform(get("/api/hospital/all")
                        .contentType(MediaType.APPLICATION_JSON).
                        param("pageNo", String.valueOf(pageNo)).
                        param("pageSize", String.valueOf(pageNo)))
                .andExpect(status().isOk()).andReturn();
        var contentAsString = mvcResult.getResponse()
                .getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(hospitalDto));
    }
    @Test
    void test_find_all_hospital_with_runtime_exception_then_return_400_status() throws Exception {
        ResponseModel responseModel = ResponseModel.of("Unable to process the request at this time");
        int pageNo = 0;
        int pageSize = 0;
        given(hospitalService.getAllHospital(pageNo, pageSize)).willThrow(NullPointerException.class);
        var mvcResult = mockMvc.perform(get("/api/hospital/all")
                        .contentType(MediaType.APPLICATION_JSON).
                        param("pageNo", String.valueOf(pageNo)).
                        param("pageSize", String.valueOf(pageNo)))
                .andExpect(status().isInternalServerError()).andReturn();
        var contentAsString = mvcResult.getResponse()
                .getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }
    @Test
    void test_find_all_hospital_with_something_went_wrong_exception_then_return_400_status() throws Exception {
        String errorMessage = "Unable to find the hospital";
        ResponseModel responseModel = ResponseModel.of(errorMessage);
        var somethingWentWrong = new SomethingWentWrong(errorMessage);
        int pageNo = 0;
        int pageSize = 0;
        given(hospitalService.getAllHospital(pageNo, pageSize)).willThrow(somethingWentWrong);
        var mvcResult = mockMvc.perform(get("/api/hospital/all")
                        .contentType(MediaType.APPLICATION_JSON).
                        param("pageNo", String.valueOf(pageNo)).
                        param("pageSize", String.valueOf(pageNo)))
                .andExpect(status().isInternalServerError()).andReturn();
        var contentAsString = mvcResult.getResponse()
                .getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }
}
