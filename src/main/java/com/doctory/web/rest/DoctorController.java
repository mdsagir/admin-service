package com.doctory.web.rest;

import com.doctory.domain.ResponseModel;
import com.doctory.domain.doctor.service.DoctorService;
import com.doctory.web.request.DoctorRequest;
import com.doctory.web.request.DoctorRequest;
import com.doctory.web.validator.AddDoctorValidation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class DoctorController {


    private final DoctorService doctorService;
    private final AddDoctorValidation addDoctorValidation;

    public DoctorController(DoctorService doctorService, AddDoctorValidation addDoctorValidation) {
        this.doctorService = doctorService;
        this.addDoctorValidation = addDoctorValidation;
    }

    /**
     * Binding apply for validation
     *
     * @param webDataBinder spring boot are apply {@link  WebDataBinder} for request body validation
     */
    @InitBinder
    public void bindValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(addDoctorValidation);
    }

    /**
     * {@code POST /api/doctor} API create a new doctor
     * <p>
     * <p>
     * Primarily validate all mandatory fields, then checks database unique constraints (given branch name existence)
     * after persist {@link DoctorRequest} all property to the database and return with success message with status {@code 201 CREATED}.
     *
     * @param doctorRequest payload {@link DoctorRequest} Its JSON API request contract send by consumer, It's not be {@literal null}.
     * @return Response entity {@link ResponseEntity} with {@link ResponseModel} status {@code 201 (Created)}
     * @throws IllegalArgumentException              in case the given {@link DoctorRequest requestBody} of its property is {@literal empty-string} or {@literal null}.
     * @throws com.doctory.common.SomethingWentWrong when anything went wrong to whole application level like database failure ...
     */
    @PostMapping
    public ResponseEntity<ResponseModel> addDoctor(@Valid @RequestBody DoctorRequest doctorRequest) {
        var addNewDoctor = doctorService.addNewDoctor(doctorRequest);
        return new ResponseEntity<>(addNewDoctor, CREATED);
    }


}
