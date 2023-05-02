package com.doctory.web.branch.validation;

import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.BranchRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class BranchJSONSerializedTest {

    @Autowired
    private JacksonTester<BranchRequest> requestJacksonTester;

    @Test
    void when_branch_request_serialization_validate_success() throws IOException {
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var branchRequest = new BranchRequest(101L, "Port luis", addressRequest);

        var jsonContent = requestJacksonTester.write(branchRequest);
        assertThat(jsonContent).extractingJsonPathNumberValue("@.hospitalId").isEqualTo(branchRequest.hospitalId().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.branchName").isEqualTo(branchRequest.branchName());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.addressLine1").isEqualTo(addressRequest.addressLine1());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.addressLine2").isEqualTo(addressRequest.addressLine2());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.pinCode").isEqualTo(addressRequest.pinCode());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.state").isEqualTo(addressRequest.state());
        assertThat(jsonContent).extractingJsonPathStringValue("@.addressRequest.country").isEqualTo(addressRequest.country());
    }

    @Test
    void when_branch_name_deserialized() throws Exception {
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var branchRequest = new BranchRequest(101L, "Port luis", addressRequest);

        var jsonContent = """
                {
                   "hospitalId":101,
                   "branchName":"Port luis",
                   "addressRequest":{
                      "addressLine1":"Address1",
                      "addressLine2":"Address2",
                      "pinCode":"898765",
                      "state":"Bihar",
                      "country":"India"
                   }
                }
                """;
        ObjectContent<BranchRequest> parse = requestJacksonTester.parse(jsonContent);
        assertThat(parse.getObject()).isEqualTo(branchRequest);
    }
}
