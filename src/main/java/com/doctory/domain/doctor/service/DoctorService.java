package com.doctory.domain.doctor.service;

import com.doctory.domain.ResponseModel;
import com.doctory.domain.SearchDto;
import com.doctory.domain.doctor.dto.DoctorDto;
import com.doctory.web.request.DoctorRequest;

import java.util.List;

public interface DoctorService {
    /**
     * Add new doctor take input {@link DoctorRequest} and mapped to JPA Entity to persist.
     * <p>
     *
     * @param doctorRequest request body containing all property
     * @return {@link ResponseModel} success message with
     */
    ResponseModel addNewDoctor(DoctorRequest doctorRequest);

    /**
     * Search by given text if any record contain prefix or post fix.
     * <p>
     *
     * @param text input data
     * @return {@link  List} of {@link SearchDto} search result
     */
    List<SearchDto> searchDoctor(String text);

    /**
     * Find a particular doctor for given {@literal id }
     * <p>
     *
     * @param id request data to be find data from persist layer
     * @return {@link  List} of {@link SearchDto} search result
     */
    DoctorDto getDoctorInfo(Long id);

    /**
     * Update doctor info for a particular {@literal id}
     *
     * @param id              to find which doctor are to edit
     * @param branchId             branch related to doctor
     * @param doctorRequest contains all the property to be updated
     * @return {@link ResponseModel} class in case
     */
    ResponseModel updateDoctorInfo(Long id,Long branchId, DoctorRequest doctorRequest);

    /**
     * Find all the doctor in pagination
     * <p>
     *
     * @param pageNo   default is {@code 0}
     * @param pageSize no of array size
     * @return {@link List} of {@link DoctorDto} based on pagination
     */
    List<DoctorDto> getAllDoctor(Integer pageNo, Integer pageSize);
}
