package com.doctory.web.hospital;

import com.doctory.domain.hospital.service.HospitalService;
import com.doctory.web.request.HospitalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HospitalControllerTest {


    @Autowired
    private HospitalService hospitalService;
    void test() {
        var hospitalRequest = new HospitalRequest("AK Hospital", "1989", "Address1",
                "Address2", "898765", "Bihar", "India");


    }
}
