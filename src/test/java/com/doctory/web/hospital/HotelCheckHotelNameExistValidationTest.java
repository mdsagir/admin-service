package com.doctory.web.hospital;


import com.doctory.web.request.HospitalRequest;
import com.doctory.web.validator.AddHospitalValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

@SpringBootTest
class HotelCheckHotelNameExistValidationTest {

    @Autowired
    private  AddHospitalValidator addHospitalValidator;
    @Test
    void test() {
        var hospitalRequest = new HospitalRequest("AK Hospital", "1989", "Address1", "Address2", "898765", "Bihar", "India");
        Errors errors = new BeanPropertyBindingResult(hospitalRequest, "");
        addHospitalValidator.validate(hospitalRequest,errors);
    }

}
