package com.doctory.domain.hospital.service;


import com.doctory.domain.ResponseModel;
import com.doctory.web.request.AddHospital;

public interface HospitalService {
    ResponseModel addNewHospital(AddHospital hospital);
}
