package com.doctory.web.rest;

import com.doctory.web.request.AddHospital;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hospital")
public class HospitalController {


    public void createHotel(@Valid @RequestBody AddHospital hospital) {

    }
}
