package com.doctory.domain;

import com.doctory.common.DataNotFoundException;
import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.hospital.dto.HospitalDto;
import com.doctory.domain.hospital.service.HospitalManager;
import com.doctory.domain.mapper.CommonMapper;
import com.doctory.domain.mapper.HospitalMapper;
import com.doctory.infra.entity.Hospital;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.HospitalRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.doctory.domain.ResponseModel.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockBean(CommonMapper.class)
class HospitalServiceTest {

    private static final Logger log = LoggerFactory.getLogger(HospitalServiceTest.class);
    @Mock
    private HospitalRepo hospitalRepo;
    @InjectMocks
    private HospitalManager hospitalManager;
    @Mock
    private HospitalMapper hospitalMapper;


    @Test
    void when_create_new_hospital_then_return_success_200() {
        var expected = of("Hospital added successfully");
        var newAddressRequest = new AddressRequest("AddressLine1", "Address2", "898765", "Bihar", "India");
        var hospitalRequest = new HospitalRequest("AK Hospital", "1999", newAddressRequest);

        Hospital hospital = new Hospital();
        hospital.setHospitalName(hospitalRequest.hospitalName());
        hospital.setFoundedAt(hospitalRequest.foundedAt());
        when(hospitalMapper.toHospitalEntity(hospitalRequest)).thenReturn(hospital);
        var responseModel = hospitalManager.addNewHospital(hospitalRequest);
        assertThat(responseModel).isEqualTo(expected);
    }

    @Test
    void when_create_new_hospital_with_runtime_exception_then_return_exception() {

        var newAddressRequest = new AddressRequest("AddressLine1", "Address2", "898765", "Bihar", "India");
        var hospitalRequest = new HospitalRequest("AK Hospital", "1999", newAddressRequest);

        when(hospitalMapper.toHospitalEntity(hospitalRequest)).thenThrow(NullPointerException.class);
        assertThatThrownBy(() -> hospitalManager.addNewHospital(hospitalRequest)).isInstanceOf(SomethingWentWrong.class)
                .hasMessage("Unable to save the hospital");

    }

    @Test
    void when_create_new_hospital_with_exception_then_return_exception() {
        var newAddressRequest = new AddressRequest("AddressLine1", "Address2", "898765", "Bihar", "India");
        var hospitalRequest = new HospitalRequest("AK Hospital", "1999", newAddressRequest);

        Hospital hospital = new Hospital();
        hospital.setHospitalName(hospitalRequest.hospitalName());
        hospital.setFoundedAt(hospitalRequest.foundedAt());
        when(hospitalMapper.toHospitalEntity(hospitalRequest)).thenReturn(hospital);
        when(hospitalRepo.save(hospital)).thenThrow(NullPointerException.class);
        assertThatThrownBy(() -> hospitalManager.addNewHospital(hospitalRequest)).isInstanceOf(SomethingWentWrong.class)
                .hasMessage("Unable to save the hospital");
    }
    /*
     * ===================================================================
     *              HOSPITAL FIND BY ID TEST CASES
     * ===================================================================
     */

    @Test
    void when_given_id_present_in_data_base_return_success() {

        Long id = 100L;
        Hospital hospital = new Hospital();
        hospital.setHospitalName("AK hospital");
        hospital.setFoundedAt("1998");
        var hospitalDto = HospitalDto.of(id, "AK Hospital", "1989",
                "addressLine1", "addressLine2", "854633",
                "Bihar", "India", LocalDateTime.now(), LocalDateTime.now());
        when(hospitalRepo.getHospitalById(id)).thenReturn(Optional.of(hospital));
        when(hospitalMapper.toHospitalDto(hospital)).thenReturn(hospitalDto);
        var hospitalInfo = hospitalManager.getHospitalInfo(id);

        assertThat(hospitalInfo).isEqualTo(hospitalDto);

    }

    @Test
    void when_given_id_not_present_in_return_success_exception() {

        Long id = 100L;
        var dataNotFoundException = new DataNotFoundException(id + " is not found");
        when(hospitalRepo.getHospitalById(id)).thenThrow(dataNotFoundException);
        assertThatThrownBy(() -> hospitalManager.getHospitalInfo(id)).isInstanceOf(DataNotFoundException.class)
                .hasMessage(id + " is not found");

    }
}
