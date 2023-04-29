package com.doctory.domain.hospital.service;


import com.doctory.domain.ResponseModel;
import com.doctory.domain.hospital.dto.HospitalDto;
import com.doctory.domain.SearchDto;
import com.doctory.web.request.HospitalRequest;
import com.doctory.web.request.UpdateHospitalRequest;

import java.util.List;

public interface HospitalService {
    /**
     * Add new hospital take input {@link HospitalRequest} and mapped to JPA Entity to persist.
     * <p>
     *
     * @param hospitalRequest request body containing all property
     * @return {@link ResponseModel} success message with
     */
    ResponseModel addNewHospital(HospitalRequest hospitalRequest);

    /**
     * Search by given text if any record contain prefix or post fix.
     * <p>
     *
     * @param text input data
     * @return {@link  List} of {@link SearchDto} search result
     */
    List<SearchDto> searchHospital(String text);

    /**
     * Find a particular hospital for given {@literal id }
     * <p>
     *
     * @param id request data to be find data from persist layer
     * @return {@link  List} of {@link SearchDto} search result
     */
    HospitalDto getHospitalInfo(Long id);

    /**
     * Update hospital info for a particular hospital
     *
     * @param updateHospitalRequest contains all the property to be updated
     * @return {@link ResponseModel} class in case
     */
    ResponseModel updateHospitalInfo(UpdateHospitalRequest updateHospitalRequest);

    /**
     * Find all the hospital in pagination
     * <p>
     *
     * @param pageNo   default is {@code 0}
     * @param pageSize no of array size
     * @return {@link List} of {@link HospitalDto} based on pagination
     */
    List<HospitalDto> getAllHospital(Integer pageNo, Integer pageSize);
}
