package com.doctory.web.rest;

import com.doctory.domain.ResponseModel;
import com.doctory.domain.hospital.service.HospitalService;
import com.doctory.web.request.AddHospital;
import com.doctory.web.validator.AddHospitalValidator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/hospital")
public class HospitalController {


    private final HospitalService hospitalService;
    private final AddHospitalValidator addHospitalValidator;


    @InitBinder
    public void bindValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(addHospitalValidator);
    }

    public HospitalController(HospitalService hospitalService, AddHospitalValidator addHospitalValidator) {
        this.hospitalService = hospitalService;
        this.addHospitalValidator = addHospitalValidator;
    }

    /**
     * {@code POST /hospital create a new user}
     *
     * <p>
     * create a new hospital if name is not exists.
     *
     * @param hospital payload {@link AddHospital} Its hold the hospital information along
     *                 with his address.
     * @return Response entity {@link ResponseEntity} with status {@code 201 (Created)}
     * with success or with status {@code 400 (Bad requested)}
     */
    @PostMapping
    public ResponseEntity<ResponseModel> createHotel(@Valid @RequestBody AddHospital hospital) {
        ResponseModel responseModel = hospitalService.addNewHospital(hospital);
        return new ResponseEntity<>(responseModel, CREATED);
    }
}
