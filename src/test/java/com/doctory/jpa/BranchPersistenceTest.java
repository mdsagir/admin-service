package com.doctory.jpa;

import com.doctory.config.DataConfig;
import com.doctory.infra.entity.Address;
import com.doctory.infra.entity.Branch;
import com.doctory.infra.entity.Common;
import com.doctory.infra.entity.Hospital;
import com.doctory.infra.repo.BranchRepo;
import com.doctory.infra.repo.HospitalRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.PageRequest.of;

@DataJpaTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class BranchPersistenceTest {


    @Autowired
    private HospitalRepo hospitalRepo;
    @Autowired
    private BranchRepo branchRepo;

    @Test
    void when_save_and_update_branch_then_return_success() {

        var branch = newBranch();
        branchRepo.save(branch);
        var all = hospitalRepo.findAll();
        assertThat(all).hasSize(1);

    }


    @Test
    void when_find_branch_by_id_then_return_success() {
        var branch = newBranch();
        branchRepo.save(branch);
        var branchOptional = branchRepo.findByBranchName(branch.getBranchName());
        assertThat(branchOptional).isNotEmpty();
    }

    @Test
    void when_find_branch_by_id_then_return_empty() {
        var hospitalOptional = hospitalRepo.findByHospitalName("$5^");
        assertThat(hospitalOptional).isEmpty();
    }
    @Test
    void when_search_branch_return_success() {
        var branch = newBranch();
        branchRepo.save(branch);
        var pageRequest = of(0, 50);
        var searchDtoList = branchRepo.searchByBranchNameContaining("port", pageRequest);
        assertThat(searchDtoList).isNotEmpty();
    }
    @Test
    void when_get_branch_by_id_then_return_success() {
        var branch = newBranch();
        branchRepo.save(branch);
        var branchOptional = branchRepo.findBranchById(branch.getId());
        assertThat(branchOptional).isNotEmpty();
    }
    @Test
    void when_get_all_branch_then_return_success() {
        var branch = newBranch();
        branchRepo.save(branch);
        var pageRequest = of(0, 50);
        var branchRepoAllBranch = branchRepo.getAllBranch(pageRequest);
        assertThat(branchRepoAllBranch).isNotEmpty();
    }


    private Branch newBranch() {
        var address = new Address("addressLine1", "addressLine2", "state",
                "country", "pinCode");
        var common = new Common();
        common.setCreatedBy(1L);
        common.setModifiedBy(1L);
        common.setCreatedDate(now());
        common.setModifiedDate(now());
        common.setFlag(true);
        common.setStatus(true);

        var hospital = new Hospital();
        hospital.setId(1L);
        hospital.setHospitalName("AA");
        hospital.setCommon(common);
        hospital.setAddress(address);
        hospital.setFoundedAt("1998");
        hospitalRepo.save(hospital);


        var branch = new Branch();
        branch.setBranchName("Port luis");
        branch.setCommon(common);
        branch.setAddress(address);
        branch.setHospital(hospital);
        return branch;
    }

}
