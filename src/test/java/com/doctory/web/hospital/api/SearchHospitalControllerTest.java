package com.doctory.web.hospital.api;

import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.ResponseModel;
import com.doctory.domain.SearchDto;
import com.doctory.domain.hospital.service.HospitalService;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.rest.HospitalController;
import com.doctory.web.validator.AddHospitalValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static com.doctory.domain.ResponseModel.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HospitalController.class)
@Import(AddHospitalValidator.class)
@MockBean(HospitalRepo.class)
class SearchHospitalControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HospitalService hospitalService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_search_hospital_with_success_validation_then_return_200_status() throws Exception {

        String search = "hospital";
        var searchDtoList = List.of(new SearchDto(1L, "AK Hospital"));
        given(hospitalService.searchHospital(search)).willReturn(searchDtoList);
        var mvcResult = mockMvc.perform(get("/api/hospital/search")
                        .contentType(MediaType.APPLICATION_JSON).param("search", search))
                .andExpect(status().isOk()).andReturn();
        var contentAsString = mvcResult.getResponse()
                .getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(searchDtoList));
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void test_search_hospital_with_failed_validation_then_return_400_status(String search) throws Exception {

        var error = Map.of("error", "The text must be defined");

        var mvcResult = mockMvc.perform(get("/api/hospital/search")
                        .contentType(MediaType.APPLICATION_JSON).param("search", search))
                .andExpect(status().isBadRequest()).andReturn();
        var contentAsString = mvcResult.getResponse()
                .getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(error));
    }

    @Test
    void test_search_hospital_with_runtime_exception_then_return_500_status() throws Exception {
        ResponseModel responseModel = of("Unable to process the request at this time");
        String search = "hospital";
        given(hospitalService.searchHospital(search)).willThrow(NullPointerException.class);
        var mvcResult = mockMvc.perform(get("/api/hospital/search")
                        .contentType(MediaType.APPLICATION_JSON).param("search", search))
                .andExpect(status().isInternalServerError()).andReturn();
        var contentAsString = mvcResult.getResponse()
                .getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }

    @Test
    void test_search_hospital_with_something_went_wrong_exception_then_return_500_status() throws Exception {
        String errorMessage = "Unable to search the hospital";
        ResponseModel responseModel = of(errorMessage);

        var somethingWentWrong = new SomethingWentWrong(errorMessage);
        String search = "hospital";
        given(hospitalService.searchHospital(search)).willThrow(somethingWentWrong);
        var mvcResult = mockMvc.perform(get("/api/hospital/search")
                        .contentType(MediaType.APPLICATION_JSON).param("search", search))
                .andExpect(status().isInternalServerError()).andReturn();
        var contentAsString = mvcResult.getResponse()
                .getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }
}
