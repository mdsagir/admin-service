package com.doctory.web.json;

import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.HospitalRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class HospitalJSONSerializedTest {

    @Autowired
    private JacksonTester<HospitalRequest> requestJacksonTester;


    @Test
    void test_hospital_request_body_serialized() throws IOException {

        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var hospitalRequest = new HospitalRequest("AK Hospital", "1989", addressRequest);

        var jsonContent = requestJacksonTester.write(hospitalRequest);
        assertThat(jsonContent).extractingJsonPathStringValue("@.hospitalName").isEqualTo(hospitalRequest.hospitalName());
        assertThat(jsonContent).extractingJsonPathStringValue("@.foundedAt").isEqualTo(hospitalRequest.foundedAt());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.addressLine1").isEqualTo(addressRequest.addressLine1());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.addressLine2").isEqualTo(addressRequest.addressLine2());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.pinCode").isEqualTo(addressRequest.pinCode());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.state").isEqualTo(addressRequest.state());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.country").isEqualTo(addressRequest.country());

    }

    @Test
    void test_hospital_request_body_deserialized() throws Exception {
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var hospitalRequest = new HospitalRequest("AK Hospital", "1989", addressRequest);

        var jsonContent = """
                {
                   "hospitalName":"AK Hospital",
                   "foundedAt":"1989",
                   "addressRequest":{
                      "addressLine1":"Address1",
                      "addressLine2":"Address2",
                      "pinCode":"898765",
                      "state":"Bihar",
                      "country":"India"
                   }
                }
                """;
        ObjectContent<HospitalRequest> parse = requestJacksonTester.parse(jsonContent);
        assertThat(parse).isEqualTo(hospitalRequest);
    }
}
