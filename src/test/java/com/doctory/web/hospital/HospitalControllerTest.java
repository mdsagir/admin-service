package com.doctory.web.hospital;

import com.doctory.domain.ResponseModel;
import com.doctory.domain.hospital.service.HospitalService;
import com.doctory.infra.entity.Hospital;
import com.doctory.infra.repo.HospitalRepo;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(HospitalController.class)
@Import(AddHospitalValidator.class)
class HospitalControllerTest {


    @MockBean
    private HospitalService hospitalService;
    @MockBean
    private HospitalRepo hospitalRepo;

    @Autowired
    private AddHospitalValidator addHospitalValidator;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void test_create_new_hospital_and_return_success_201_status() throws Exception {

        var hospitalRequest = new HospitalRequest("AK Hospital", "1989", "Address1", "Address2", "898765", "Bihar", "India");
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
    void test_create_new_and_validate() throws Exception {
        var hospitalRequest = new HospitalRequest("AK Hospital", "1989", "Address1", "Address2", "898765", "Bihar", "India");

        var hospital=new Hospital();
        hospital.setHospitalName(hospital.getHospitalName());
        var hospitalOptional = Optional.of(hospital);


        var errors = new BeanPropertyBindingResult(hospitalRequest, "hospitalRequest");
        addHospitalValidator.validate(hospitalRequest,errors);
        given(hospitalRepo.findByHospitalName(hospitalRequest.hospitalName())).willReturn(hospitalOptional);
        assertThat(errors.hasErrors()).isEqualTo(Boolean.FALSE);

    }
}
