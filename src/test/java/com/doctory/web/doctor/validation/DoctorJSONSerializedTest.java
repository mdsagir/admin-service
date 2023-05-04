package com.doctory.web.doctor.validation;


import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.DoctorRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;

import java.io.IOException;

import static com.doctory.web.request.PersonRequest.of;
import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class DoctorJSONSerializedTest {

    @Autowired
    private JacksonTester<DoctorRequest> requestJacksonTester;


    @Test
    void test_add_hospital_request_body_serialized() throws IOException {

        var personRequest = of("AA", "BB", "CC", "908979780", "2343214532", "aa@gmail.com");
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");

        var doctorRequest = new DoctorRequest(1L, "D", "surgeon", "heart", addressRequest, personRequest);

        var jsonContent = requestJacksonTester.write(doctorRequest);
        assertThat(jsonContent).extractingJsonPathNumberValue("@.branchId").isEqualTo(doctorRequest.branchId().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.doctorDegree").isEqualTo(doctorRequest.doctorDegree());
        assertThat(jsonContent).extractingJsonPathStringValue("@.practiceName").isEqualTo(doctorRequest.practiceName());
        assertThat(jsonContent).extractingJsonPathStringValue("@.specialist").isEqualTo(doctorRequest.specialist());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.addressLine1").isEqualTo(addressRequest.addressLine1());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.addressLine2").isEqualTo(addressRequest.addressLine2());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.pinCode").isEqualTo(addressRequest.pinCode());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.state").isEqualTo(addressRequest.state());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.country").isEqualTo(addressRequest.country());

        assertThat(jsonContent).extractingJsonPathStringValue("@.personRequest.firstName").isEqualTo(personRequest.firstName());
        assertThat(jsonContent).extractingJsonPathStringValue("@.personRequest.lastName").isEqualTo(personRequest.lastName());
        assertThat(jsonContent).extractingJsonPathStringValue("@.personRequest.surname").isEqualTo(personRequest.surname());
        assertThat(jsonContent).extractingJsonPathStringValue("@.personRequest.mobileNo").isEqualTo(personRequest.mobileNo());
        assertThat(jsonContent).extractingJsonPathStringValue("@.personRequest.alternateNo").isEqualTo(personRequest.alternateNo());
        assertThat(jsonContent).extractingJsonPathStringValue("@.personRequest.email").isEqualTo(personRequest.email());

    }

    @Test
    void when_add_doctor_request_body_deserialized() throws Exception {
        var personRequest = of("AA", "BB", "CC", "908979780", "2343214532", "aa@gmail.com");
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");

        var doctorRequest = new DoctorRequest(1L, "D", "surgeon", "heart", addressRequest, personRequest);

        var jsonContent = """
                {
                   "branchId":1,
                   "doctorDegree":"D",
                   "practiceName":"surgeon",
                   "specialist":"heart",
                   "addressRequest":{
                      "addressLine1":"Address1",
                      "addressLine2":"Address2",
                      "pinCode":"898765",
                      "state":"Bihar",
                      "country":"India"
                   },
                   "personRequest":{
                      "firstName":"AA",
                      "lastName":"BB",
                      "surname":"CC",
                      "mobileNo":"908979780",
                      "alternateNo":"2343214532",
                      "email":"aa@gmail.com"
                   }
                }
                """;
        ObjectContent<DoctorRequest> parse = requestJacksonTester.parse(jsonContent);
        assertThat(parse.getObject()).isEqualTo(doctorRequest);
    }

}
