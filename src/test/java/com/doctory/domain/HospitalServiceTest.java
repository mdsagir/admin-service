package com.doctory.domain;

import com.doctory.common.DataNotFoundException;
import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.hospital.service.HospitalManager;
import com.doctory.domain.mapper.CommonMapper;
import com.doctory.domain.mapper.HospitalMapper;
import com.doctory.infra.entity.Hospital;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.HospitalRequest;
import com.doctory.web.request.UpdateHospitalRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.doctory.domain.ResponseModel.of;
import static com.doctory.domain.hospital.dto.HospitalDto.of;
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
        var hospitalDto = of(id, "AK Hospital", "1989",
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
    /*
        ===========================================
        UPDATE HOSPITAL TEST CASES
        ===========================================
     */

    @Test
    void when_update_hospital_info_then_return_success() {
        var expected = of("Hospital updated successfully");
        var newAddressRequest = new AddressRequest("AddressLine1", "Address2", "898765", "Bihar", "India");
        var updateHospitalRequest = new UpdateHospitalRequest(1L, "AK Hospital", "1999", newAddressRequest);

        Long id = updateHospitalRequest.id();
        Hospital hospital = new Hospital();
        hospital.setHospitalName("AK hospital");
        hospital.setFoundedAt("1111");
        when(hospitalRepo.getHospitalById(id)).thenReturn(Optional.of(hospital));
        when(hospitalMapper.toUpdateHospitalEntity(updateHospitalRequest, hospital)).thenReturn(hospital);
        var responseModel = hospitalManager.updateHospitalInfo(updateHospitalRequest);
        assertThat(responseModel).isEqualTo(expected);
    }

    @Test
    void when_given_id_not_present_for_update_then_return_exception() {

        Long id = 1L;
        when(hospitalRepo.getHospitalById(id)).thenReturn(Optional.empty());
        var updateHospitalRequest = new UpdateHospitalRequest(1L, "AK Hospital", "1999", null);
        assertThatThrownBy(() -> hospitalManager.updateHospitalInfo(updateHospitalRequest)).isInstanceOf(DataNotFoundException.class)
                .hasMessage(id + " is not found");

    }

    @Test
    void when_update_hospital_info_then_runtime_exception() {
        var expected = of("Hospital updated successfully");
        var newAddressRequest = new AddressRequest("AddressLine1", "Address2", "898765", "Bihar", "India");
        var updateHospitalRequest = new UpdateHospitalRequest(1L, "AK Hospital", "1999", newAddressRequest);

        Long id = updateHospitalRequest.id();
        Hospital hospital = new Hospital();
        hospital.setHospitalName("AK hospital");
        hospital.setFoundedAt("1111");
        when(hospitalRepo.getHospitalById(id)).thenReturn(Optional.of(hospital));
        when(hospitalMapper.toUpdateHospitalEntity(updateHospitalRequest, hospital)).thenReturn(hospital);
        var responseModel = hospitalManager.updateHospitalInfo(updateHospitalRequest);
        assertThat(responseModel).isEqualTo(expected);
    }
    /*
        =========================================
        FIND ALL DOCTOR'S
        =========================================
     */

    @Test
    void when_get_all_the_hospital_then_return_success() {

        int page = 0, pageSize = 1;

        var hospitalDtoList = List.of(of(10L, "AK Hospital", "1989",
                "addressLine1", "addressLine2", "854633",
                "Bihar", "India", LocalDateTime.now(), LocalDateTime.now()));
        var pageRequest = PageRequest.of(page, pageSize);
        var hospitalDtoPage = new PageImpl<>(hospitalDtoList);
        when(hospitalRepo.getAllHospital(pageRequest)).thenReturn(hospitalDtoPage);
        var allHospital = hospitalManager.getAllHospital(page, pageSize);
        assertThat(hospitalDtoList).isEqualTo(allHospital);
    }

    @Test
    void when_get_all_the_hospital_then_return_exception() {

        int page = 0, pageSize = 1;
        var pageRequest = PageRequest.of(page, pageSize);
        when(hospitalRepo.getAllHospital(pageRequest)).thenThrow(NullPointerException.class);
        assertThatThrownBy(() -> hospitalManager.getAllHospital(page, pageSize))
                .isInstanceOf(SomethingWentWrong.class)
                .hasMessage("Unable to find the hospital");
    }

    /*
    ========================
    SEARCH DOCTOR TEST CASE
    ========================
     */

    @Test
    void when_search_hospital_then_return_success() {

        int page = 0, pageSize = 50;
        String search = "ho";

        var hospitalSearchDto = List.of(new SearchDto(1L, "Hospital"));
        var pageRequest = PageRequest.of(page, pageSize);
        when(hospitalRepo.searchByHospitalNameContaining(search, pageRequest)).thenReturn(hospitalSearchDto);
        var searchDtoList = hospitalManager.searchHospital(search);
        assertThat(searchDtoList).isEqualTo(hospitalSearchDto);
    }

    @Test
    void when_search_hospital_then_return_exception() {

        int page = 0, pageSize = 50;
        String search = "ho";

        var pageRequest = PageRequest.of(page, pageSize);
        when(hospitalRepo.searchByHospitalNameContaining(search, pageRequest)).thenThrow(NullPointerException.class);
        assertThatThrownBy(() -> hospitalManager.searchHospital(search))
                .isInstanceOf(SomethingWentWrong.class)
                .hasMessage("Unable to search the hospital");
    }
}
