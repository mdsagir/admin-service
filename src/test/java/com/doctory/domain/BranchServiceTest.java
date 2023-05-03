package com.doctory.domain;

import com.doctory.common.DataNotFoundException;
import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.branch.service.BranchManager;
import com.doctory.domain.mapper.BranchMapper;
import com.doctory.domain.mapper.CommonMapper;
import com.doctory.infra.entity.Address;
import com.doctory.infra.entity.Branch;
import com.doctory.infra.entity.Common;
import com.doctory.infra.entity.Hospital;
import com.doctory.infra.repo.BranchRepo;
import com.doctory.infra.repo.HospitalRepo;
import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.BranchRequest;
import com.doctory.web.request.UpdateBranchRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.doctory.domain.ResponseModel.of;
import static com.doctory.domain.branch.dto.BranchDto.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockBean(CommonMapper.class)
class BranchServiceTest {

    @Mock
    private BranchRepo branchRepo;
    @InjectMocks
    private BranchManager branchManager;
    @Mock
    private BranchMapper branchMapper;

    @Mock
    private HospitalRepo hospitalRepo;

    @Test
    void when_create_new_branch_then_return_success() {
        var expected = of("Branch added successfully");

        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var branchRequest = new BranchRequest(1L, "Port luis", addressRequest);

        var hospitalId = branchRequest.hospitalId();

        when(hospitalRepo.getReferenceById(hospitalId)).thenReturn(new Hospital());
        when(branchMapper.toEntity(branchRequest)).thenReturn(new Branch());
        var responseModel = branchManager.addNewBranch(branchRequest);
        assertThat(responseModel).isEqualTo(expected);
    }

    @Test
    void when_create_new_branch_with_runtime_exception_then_return_exception() {

        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var branchRequest = new BranchRequest(1L, "Port luis", addressRequest);

        when(branchMapper.toEntity(branchRequest)).thenThrow(NullPointerException.class);
        assertThatThrownBy(() -> branchManager.addNewBranch(branchRequest)).isInstanceOf(SomethingWentWrong.class)
                .hasMessage("Unable to save the branch");

    }

    @Test
    void when_create_new_branch_with_exception_then_return_exception() {
        var addressRequest = new AddressRequest("Address1", "Address2", "898765", "Bihar", "India");
        var branchRequest = new BranchRequest(1L, "Port luis", addressRequest);

        var branch = new Branch();
        branch.setBranchName(branch.getBranchName());

        when(branchMapper.toEntity(branchRequest)).thenReturn(branch);
        when(branchRepo.save(branch)).thenThrow(NullPointerException.class);
        assertThatThrownBy(() -> branchManager.addNewBranch(branchRequest)).isInstanceOf(SomethingWentWrong.class)
                .hasMessage("Unable to save the branch");
    }
    /*
     * ===================================================================
     *              BRANCH FIND BY ID TEST CASES
     * ===================================================================
     */

    @Test
    void when_given_id_present_in_data_base_return_success() {

        Long id = 100L;
        Branch branch = new Branch();
        branch.setBranchName("Port luis");
        branch.setAddress(new Address());
        branch.setCommon(new Common());

        var branchDto = of(branch);
        when(branchRepo.findBranchById(id)).thenReturn(Optional.of(branch));
        var branchInfo = branchManager.getBranchInfo(id);
        assertThat(branchInfo).isEqualTo(branchDto);

    }

    @Test
    void when_given_id_not_present_in_return_success_exception() {

        Long id = 100L;
        var dataNotFoundException = new DataNotFoundException(id + " is not found");
        when(branchRepo.findBranchById(id)).thenThrow(dataNotFoundException);
        assertThatThrownBy(() -> branchManager.getBranchInfo(id)).isInstanceOf(DataNotFoundException.class)
                .hasMessage(id + " is not found");

    }
    /*
        ===========================================
        UPDATE HOSPITAL TEST CASES
        ===========================================
     */

    @Test
    void when_update_hospital_info_then_return_success() {
        var expected = of("Branch updated successfully");
        var newAddressRequest = new AddressRequest("AddressLine1", "Address2", "898765", "Bihar", "India");
        var updateBranchRequest = new UpdateBranchRequest(1L, 2L, "Port louis", newAddressRequest);

        Long id = updateBranchRequest.branchId();

        Branch branch = new Branch();
        branch.setBranchName("Port louis");
        when(branchRepo.findBranchById(id)).thenReturn(Optional.of(branch));
        var responseModel = branchManager.updateBranch(updateBranchRequest);
        assertThat(responseModel).isEqualTo(expected);
    }

    @Test
    void when_given_id_not_present_for_update_then_return_exception() {
        var newAddressRequest = new AddressRequest("AddressLine1", "Address2", "898765", "Bihar", "India");
        Long id = 1L;
        when(branchRepo.findBranchById(id)).thenReturn(Optional.empty());
        var updateHospitalRequest = new UpdateBranchRequest(1L, 2L, "Port louis", newAddressRequest);
        assertThatThrownBy(() -> branchManager.updateBranch(updateHospitalRequest)).isInstanceOf(DataNotFoundException.class)
                .hasMessage(id + " is not found");

    }

    @Test
    void when_update_hospital_info_then_runtime_exception() {
        var expected = of("Branch updated successfully");
        var newAddressRequest = new AddressRequest("AddressLine1", "Address2", "898765", "Bihar", "India");
        var updateBranchRequest = new UpdateBranchRequest(1L, 2L, "Port luis", newAddressRequest);

        Long id = updateBranchRequest.branchId();
        Branch branch = new Branch();
        branch.setBranchName("Port louis");
        when(branchRepo.findBranchById(id)).thenReturn(Optional.of(branch));
        var responseModel = branchManager.updateBranch(updateBranchRequest);
        assertThat(responseModel).isEqualTo(expected);
    }
    /*
        =========================================
        FIND ALL DOCTOR'S
        =========================================
     */

    @Test
    void when_get_all_the_branch_then_return_success() {

        int page = 0, pageSize = 1;


        Branch branch = new Branch();
        branch.setBranchName("Port luis");
        branch.setAddress(new Address());
        branch.setCommon(new Common());

        var branchDto = List.of(of(branch));

        var pageRequest = PageRequest.of(page, pageSize);
        var hospitalDtoPage = new PageImpl<>(branchDto);
        when(branchRepo.getAllBranch(pageRequest)).thenReturn(hospitalDtoPage);
        var allHospital = branchManager.getAllBranch(page, pageSize);
        assertThat(branchDto).isEqualTo(allHospital);
    }

    @Test
    void when_get_all_the_branch_then_return_exception() {

        int page = 0, pageSize = 1;
        var pageRequest = PageRequest.of(page, pageSize);
        when(branchRepo.getAllBranch(pageRequest)).thenThrow(NullPointerException.class);
        assertThatThrownBy(() -> branchManager.getAllBranch(page, pageSize))
                .isInstanceOf(SomethingWentWrong.class)
                .hasMessage("Unable to get the branch");
    }

    /*
    ========================
    SEARCH DOCTOR TEST CASE
    ========================
     */

    @Test
    void when_search_branch_then_return_success() {

        int page = 0, pageSize = 50;
        String search = "ho";

        var hospitalSearchDto = List.of(new SearchDto(1L, "Branch"));
        var pageRequest = PageRequest.of(page, pageSize);
        when(branchRepo.searchByBranchNameContaining(search, pageRequest)).thenReturn(hospitalSearchDto);
        var searchDtoList = branchManager.searchBranch(search);
        assertThat(searchDtoList).isEqualTo(hospitalSearchDto);
    }

    @Test
    void when_search_branch_then_return_exception() {

        int page = 0, pageSize = 50;
        String search = "ho";

        var pageRequest = PageRequest.of(page, pageSize);
        when(branchRepo.searchByBranchNameContaining(search, pageRequest)).thenThrow(NullPointerException.class);
        assertThatThrownBy(() -> branchManager.searchBranch(search))
                .isInstanceOf(SomethingWentWrong.class)
                .hasMessage("Unable to search the branch");
    }
}
