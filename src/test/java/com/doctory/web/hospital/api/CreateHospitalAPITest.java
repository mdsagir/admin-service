package com.doctory.web.hospital.api;

import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.ResponseModel;
import com.doctory.domain.hospital.service.HospitalService;
import com.doctory.infra.entity.Hospital;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.HospitalRequest;
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
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(HospitalController.class)
@Import(AddHospitalValidator.class)
class CreateHospitalAPITest {


    @MockBean
    private HospitalService hospitalService;
    @MockBean
    private HospitalRepo hospitalRepo;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_create_new_hospital_with_success_validation_then_return_201_status() throws Exception {

        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var hospitalRequest = new HospitalRequest("AK Hospital", "1989", addressRequest);
        given(hospitalRepo.findByHospitalName(hospitalRequest.hospitalName())).willReturn(Optional.empty());
        var responseModel = new ResponseModel("Hospital added successfully");
        given(hospitalService.addNewHospital(hospitalRequest)).willReturn(responseModel);
        var mvcResult = mockMvc.perform(post("/api/hospital")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hospitalRequest)))
                .andExpect(status().isCreated())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }

    @Test
    void test_create_new_hospital_with_failed_validation_then_return_400_status() throws Exception {


        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var hospitalRequest = new HospitalRequest("AK Hospital", "1989", addressRequest);
        Hospital hospital = new Hospital();
        hospital.setHospitalName(hospitalRequest.hospitalName());
        given(hospitalRepo.findByHospitalName(hospitalRequest.hospitalName())).willReturn(of(hospital));
        var mvcResult = mockMvc.perform(post("/api/hospital")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hospitalRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        var errors = Map.of("hospitalName", hospitalRequest.hospitalName()+" hospital name already exist");
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(errors));
    }
    @Test
    void test_create_new_hospital_with_runtime_exception_then_return_500_status() throws Exception {
        ResponseModel responseModel = of("Unable to process the request at this time");
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var hospitalRequest = new HospitalRequest("AK Hospital", "1989", addressRequest);
        given(hospitalRepo.findByHospitalName(hospitalRequest.hospitalName())).willReturn(Optional.empty());
        given(hospitalService.addNewHospital(hospitalRequest)).willThrow(NullPointerException.class);
        var mvcResult = mockMvc.perform(post("/api/hospital")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hospitalRequest)))
                .andExpect(status().isInternalServerError())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }
    @Test
    void test_create_new_hospital_with_SomethingWentWrong_exception_then_return_400_status() throws Exception {
        ResponseModel responseModel = of("Unable to save the hospital");
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var hospitalRequest = new HospitalRequest("AK Hospital", "1989", addressRequest);
        given(hospitalRepo.findByHospitalName(hospitalRequest.hospitalName())).willReturn(Optional.empty());
        var somethingWentWrong = new SomethingWentWrong("Unable to save the hospital");
        given(hospitalService.addNewHospital(hospitalRequest)).willThrow(somethingWentWrong);
        var mvcResult = mockMvc.perform(post("/api/hospital")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hospitalRequest)))
                .andExpect(status().isInternalServerError())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }

}
