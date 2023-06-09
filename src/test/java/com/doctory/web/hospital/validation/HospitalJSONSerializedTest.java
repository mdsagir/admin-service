package com.doctory.web.hospital.validation;

import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.HospitalRequest;
import com.doctory.web.request.UpdateHospitalRequest;
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
    @Autowired
    private JacksonTester<UpdateHospitalRequest> updateHospitalRequestJacksonTester;


    @Test
    void test_add_hospital_request_body_serialized() throws IOException {

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
    void test_add_hospital_request_body_deserialized() throws Exception {
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
        assertThat(parse.getObject()).isEqualTo(hospitalRequest);
    }

    @Test
    void test_update_hospital_request_body_serialized() throws IOException {

        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var updateHospitalRequest = new UpdateHospitalRequest(101L, "AK Hospital", "1989", addressRequest);

        var jsonContent = updateHospitalRequestJacksonTester.write(updateHospitalRequest);
        assertThat(jsonContent).extractingJsonPathNumberValue("@.id").isEqualTo(updateHospitalRequest.id().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.hospitalName").isEqualTo(updateHospitalRequest.hospitalName());
        assertThat(jsonContent).extractingJsonPathStringValue("@.foundedAt").isEqualTo(updateHospitalRequest.foundedAt());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.addressLine1").isEqualTo(addressRequest.addressLine1());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.addressLine2").isEqualTo(addressRequest.addressLine2());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.pinCode").isEqualTo(addressRequest.pinCode());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.state").isEqualTo(addressRequest.state());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.country").isEqualTo(addressRequest.country());

    }

    @Test
    void test_update_hospital_request_body_deserialized() throws Exception {
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var updateHospitalRequest = new UpdateHospitalRequest(101L, "AK Hospital", "1989", addressRequest);

        var jsonContent = """
                {
                   "id":101,
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
        ObjectContent<UpdateHospitalRequest> parse = updateHospitalRequestJacksonTester.parse(jsonContent);
        assertThat(parse.getObject()).isEqualTo(updateHospitalRequest);
    }
}
