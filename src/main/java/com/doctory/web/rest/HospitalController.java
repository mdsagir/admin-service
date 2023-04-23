package com.doctory.web.rest;

import com.doctory.domain.ResponseModel;
import com.doctory.domain.hospital.dto.HospitalDto;
import com.doctory.domain.hospital.dto.HospitalSearchDto;
import com.doctory.domain.hospital.service.HospitalService;
import com.doctory.web.request.HospitalRequest;
import com.doctory.web.validator.AddHospitalValidator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import java.util.List;

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
     * @param hospital payload {@link HospitalRequest} Its hold the hospital information along
     *                 with his address.
     * @return Response entity {@link ResponseEntity} with status {@code 201 (Created)}
     * with success or with status {@code 400 (Bad requested)}
     */
    @PostMapping
    public ResponseEntity<ResponseModel> createHospital(@Valid @RequestBody HospitalRequest hospital) {
        ResponseModel responseModel = hospitalService.addNewHospital(hospital);
        return new ResponseEntity<>(responseModel, CREATED);
    }

    /**
     * {@code GET /hospital find the hospital }
     * <p>
     *   Find the Hospital info for given unique identifier get, if the hospital are available return
     *   {@link HospitalDto} with {@code 200 (Success)}, if hospital not exist api return {@code 404 (Not found)}
     *
     * @param id input hospital id
     * @return Response entity {@link HospitalDto} contain all information of particular hospital
     */
    @GetMapping
    public ResponseEntity<HospitalDto> getHospitalInfo(@RequestParam Long id) {
        HospitalDto hospitalInfo = hospitalService.getHospitalInfo(id);
        return new ResponseEntity<>(hospitalInfo, OK);
    }

    @PutMapping
    public ResponseEntity<HospitalDto> updateHospitalInfo(@RequestParam Long id, @Valid @RequestBody HospitalRequest hospital) {
        HospitalDto hospitalDto = hospitalService.updateHospitalInfo(id, hospital);
        return new ResponseEntity<>(hospitalDto, OK);
    }

    @GetMapping("search")
    public ResponseEntity<List<HospitalSearchDto>> getHospitalInfo(@RequestParam String search) {
        List<HospitalSearchDto> hospitalSearch = hospitalService.searchHospital(search);
        return new ResponseEntity<>(hospitalSearch, OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<HospitalDto>> getAllHospital(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                                           @RequestParam(required = false,defaultValue = "0") Integer pageSize) {
        List<HospitalDto> hospitals = hospitalService.getAllHospital(pageNo, pageSize);
        return new ResponseEntity<>(hospitals, OK);
    }

}