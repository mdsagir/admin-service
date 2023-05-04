package com.doctory.web.doctor.api;

import com.doctory.domain.ResponseModel;
import com.doctory.domain.doctor.service.DoctorService;
import com.doctory.infra.entity.Doctor;
import com.doctory.infra.entity.Person;
import com.doctory.infra.repo.DoctorRepo;
import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.DoctorRequest;
import com.doctory.web.rest.DoctorController;
import com.doctory.web.validator.AddDoctorValidation;
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

import static com.doctory.web.request.AddressRequest.of;
import static com.doctory.web.request.DoctorRequest.of;
import static com.doctory.web.request.PersonRequest.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DoctorController.class)
@Import(AddDoctorValidation.class)
class CreateDoctorAPITest {


    private final String URL = "/api/doctor";

    @MockBean
    private DoctorService doctorService;
    @MockBean
    private DoctorRepo doctorRepo;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void when_create_new_doctor_with_success_validation_then_return_201_status() throws Exception {

        var personRequest = of("AA", "BB", "CC", "908979780", "2343214532", "aa@gmail.com");
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var doctorRequest = new DoctorRequest(1L, "D", "surgeon", "heart", addressRequest, personRequest);

        given(doctorRepo.findByPerson_FirstName(doctorRequest.personRequest().firstName())).willReturn(Optional.empty());
        var responseModel = new ResponseModel("Doctor added successfully");
        given(doctorService.addNewDoctor(doctorRequest)).willReturn(responseModel);
        var mvcResult = mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorRequest)))
                .andExpect(status().isCreated())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(responseModel));
    }

    @Test
    void when_create_new_doctor_with_failed_validation_then_return_400_status() throws Exception {


        var personRequest = of("AA", "BB", "CC", "908979780", "2343214532", "aa@gmail.com");
        var addressRequest =  of("Address1", "Address2", "898765", "Bihar", "India");
        var doctorRequest = of(1L, "D", "surgeon", "heart", addressRequest, personRequest);

        var doctor = new Doctor();
        var person = new Person();
        person.setFirstName("A");
        doctor.setPerson(person);

        given(doctorRepo.findByPerson_FirstName("AA")).willReturn(Optional.of(doctor));
        var mvcResult = mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        var errors = Map.of("personRequest.firstName", doctor.getPerson().getFirstName() + " doctor name already exist");
        assertThat(contentAsString).isEqualTo(objectMapper.writeValueAsString(errors));
    }
}
