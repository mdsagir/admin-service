package com.doctory.domain;

import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.hospital.service.HospitalManager;
import com.doctory.domain.mapper.CommonMapper;
import com.doctory.domain.mapper.HospitalMapper;
import com.doctory.infra.entity.Hospital;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.HospitalRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.doctory.domain.ResponseModel.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockBean(CommonMapper.class)
class HospitalServiceTest {


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
}
