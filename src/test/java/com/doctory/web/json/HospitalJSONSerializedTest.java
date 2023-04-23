package com.doctory.web.json;

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

        var hospitalRequest = new HospitalRequest("AK Hospital", "1989", "Address1", "Address2", "898765", "Bihar", "India");
        var jsonContent = requestJacksonTester.write(hospitalRequest);
        assertThat(jsonContent).extractingJsonPathStringValue("@.hospitalName").isEqualTo(hospitalRequest.hospitalName());
        assertThat(jsonContent).extractingJsonPathStringValue("@.foundedAt").isEqualTo(hospitalRequest.foundedAt());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressLine1").isEqualTo(hospitalRequest.addressLine1());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressLine2").isEqualTo(hospitalRequest.addressLine2());
        assertThat(jsonContent).extractingJsonPathStringValue("@.pinCode").isEqualTo(hospitalRequest.pinCode());
        assertThat(jsonContent).extractingJsonPathStringValue("@.state").isEqualTo(hospitalRequest.state());
        assertThat(jsonContent).extractingJsonPathStringValue("@.country").isEqualTo(hospitalRequest.country());

    }

    @Test
    void test_hospital_request_body_deserialized() throws Exception {
        var hospitalRequest = new HospitalRequest("AK Hospital", "1989", "Address1", "Address2", "898765", "Bihar", "India");

        var jsonContent = """
                {
                   "hospitalName":"AK Hospital",
                   "foundedAt":"1989",
                   "addressLine1":"Address1",
                   "addressLine2":"Address2",
                   "pinCode":"898765",
                   "state":"Bihar",
                   "country":"India"
                }
                """;
        ObjectContent<HospitalRequest> parse = requestJacksonTester.parse(jsonContent);
        assertThat(parse).isEqualTo(hospitalRequest);
    }
}
