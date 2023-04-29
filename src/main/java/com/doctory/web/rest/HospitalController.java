package com.doctory.web.rest;

import com.doctory.domain.ResponseModel;
import com.doctory.domain.hospital.dto.HospitalDto;
import com.doctory.domain.SearchDto;
import com.doctory.domain.hospital.service.HospitalService;
import com.doctory.web.request.HospitalRequest;
import com.doctory.web.validator.AddHospitalValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

/**
 * <p>
 * {@link  HospitalController} are used for {@code CRUD} operation for the Hostel module,
 * that perform {@code add}, {@code update}, {@code find}, {@code findAll}, {@code search}
 * operation
 */

@Validated
@RestController
@RequestMapping("/api/hospital")
public class HospitalController {


    private final HospitalService hospitalService;
    private final AddHospitalValidator addHospitalValidator;


    /**
     * Binding apply for validation
     *
     * @param webDataBinder spring boot are apply {@link  WebDataBinder}
     *                      for request body validation
     */
    @InitBinder
    public void bindValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(addHospitalValidator);
    }

    // dependency injection by constructor injection
    public HospitalController(HospitalService hospitalService, AddHospitalValidator addHospitalValidator) {
        this.hospitalService = hospitalService;
        this.addHospitalValidator = addHospitalValidator;
    }

    /**
     * {@code POST /api/hospital} API create a new hospital
     * <p>
     * Primarily validate all mandatory fields, then checks database
     * unique constraints <b>given hospital name existence</b> after
     * persist {@link HospitalRequest} all property to the database
     * and return with success message with status {@code 201 CREATED}.
     *
     * @param hospital payload {@link HospitalRequest} Its JSON API request
     *                 contract send by consumer, It's not be {@literal null}.
     * @return Response entity {@link ResponseEntity} with {@link ResponseModel}
     * status {@code 201 (Created)}
     * @throws IllegalArgumentException              in case the given {@link HospitalRequest requestBody}
     *                                               of its property is {@literal empty-string} or {@literal null}.
     * @throws com.doctory.common.SomethingWentWrong when anything went wrong to
     *                                               whole application level like database failure ...
     */
    @PostMapping
    public ResponseEntity<ResponseModel> createHospital(@Valid @RequestBody HospitalRequest hospital) {
        var responseModel = hospitalService.addNewHospital(hospital);
        return new ResponseEntity<>(responseModel, CREATED);
    }

    /**
     * {@code GET api/hospital} get hospital info by id
     * <p>
     *
     * @param id input request parameter, It's not be {@literal null}.
     * @return {@link ResponseEntity} with {@link HospitalDto} contain all information
     * of particular hospital with {@code 200} Success
     * @throws IllegalArgumentException                 in case the given {@literal  id parameter} of its
     *                                                  property is {@literal empty-string} or {@literal null}
     *                                                  then validation exception are triggered and response
     *                                                  give the {@code 400 Bad request}
     * @throws com.doctory.common.DataNotFoundException in case the given invalid {@literal  id} that no record
     *                                                  available and response give the {@code 404 Not found}
     * @throws com.doctory.common.SomethingWentWrong    when anything went wrong to whole application level like
     *                                                  database failure and response the {@code 500 Internal server error}
     */
    @GetMapping
    public ResponseEntity<HospitalDto> getHospitalInfo(@RequestParam(required = false) @NotNull(message = "The id must be defined") Long id) {
        var hospitalInfo = hospitalService.getHospitalInfo(id);
        return new ResponseEntity<>(hospitalInfo, OK);
    }

    /**
     * {@code PUT api/hospital} update hospital info by id
     * <p>
     * Finds the existence Hospital by given {@literal  id} and
     * update Hospital data by {@link HospitalRequest}
     *
     * @param id       input request parameter, It's not be {@literal null},
     *                 that fetch the existing Hospital details
     * @param hospital payload {@link HospitalRequest} Its JSON API request contract send by
     *                 consumer, It's not be {@literal null}.
     * @return Response entity {@link ResponseEntity} with {@link ResponseModel} status {@code 200 (SUCCESS)}
     * @throws IllegalArgumentException                 in case the given {@link HospitalRequest requestBody} of its property
     *                                                  is {@literal empty-string} or {@literal null}
     *                                                  and response give the {@code 400 Bad request}
     * @throws com.doctory.common.DataNotFoundException in case the given invalid {@literal  id} that no record available
     *                                                  and response give the {@code 404 Not found}
     * @throws com.doctory.common.SomethingWentWrong    when anything went wrong to whole application level like database
     *                                                  failure and response the {@code 500 Internal server error}
     */

    @PutMapping
    public ResponseEntity<ResponseModel> updateHospitalInfo(@RequestParam Long id, @Valid @RequestBody HospitalRequest hospital) {
        var responseModel = hospitalService.updateHospitalInfo(id, hospital);
        return new ResponseEntity<>(responseModel, OK);
    }

    /**
     * {@code PUT api/hospital/search} search hospital by text
     * <p>
     * Perform search by given text with {@literal 50} size pagination,
     * if not match any result return empty array
     *
     * @param search input request parameter, It's not be {@literal null}.
     * @throws com.doctory.common.SomethingWentWrong when anything went wrong to whole application level
     *                                               like database failure and response the {@code 500 Internal server error}
     */
    @GetMapping("search")
    public ResponseEntity<List<SearchDto>> getHospitalInfo(@RequestParam String search) {
        var hospitalSearch = hospitalService.searchHospital(search);
        return new ResponseEntity<>(hospitalSearch, OK);
    }

    /**
     * {@code PUT api/hospital/all} search hospital by text
     *
     * @param pageNo   current page no which data are display
     * @param pageSize array page size default is {@literal 10} size
     * @return {@link ResponseEntity} with {@link List} of {@link HospitalDto} hospital details
     * based on page nation with {@code 200} Success
     * @throws com.doctory.common.SomethingWentWrong when anything went wrong to whole application level
     *                                               like database failure and response the {@code 500 Internal server error}
     */
    @GetMapping("all")
    public ResponseEntity<List<HospitalDto>> getAllHospital(@RequestParam(required = false, defaultValue = "0") Integer pageNo, @RequestParam(required = false, defaultValue = "0") Integer pageSize) {
        var hospitals = hospitalService.getAllHospital(pageNo, pageSize);
        return new ResponseEntity<>(hospitals, OK);
    }

}
