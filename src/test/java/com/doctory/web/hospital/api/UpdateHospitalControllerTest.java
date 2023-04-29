package com.doctory.web.hospital.api;

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

@WebMvcTest(HospitalController.class)
@Import(AddHospitalValidator.class)
@MockBean(HospitalRepo.class)
class UpdateHospitalControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HospitalService hospitalService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test() {

    }
}
