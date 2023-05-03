package com.doctory.jpa;

import com.doctory.config.DataConfig;
import com.doctory.infra.entity.Address;
import com.doctory.infra.entity.Common;
import com.doctory.infra.entity.Hospital;
import com.doctory.infra.repo.HospitalRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.PageRequest.of;

@DataJpaTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class HospitalPersistenceTest {


    @Autowired
    private HospitalRepo hospitalRepo;


    @Test
    void when_save_and_update_doctor_then_return_success() {

        var hospital = newHospital();
        hospitalRepo.save(hospital);

        var all = hospitalRepo.findAll();
        assertThat(all).hasSize(1);

    }

    @Test
    void when_find_hospital_by_id_then_return_success() {
        var hospital = newHospital();
        hospitalRepo.save(hospital);
        var hospitalOptional = hospitalRepo.findByHospitalName(hospital.getHospitalName());
        assertThat(hospitalOptional).isNotEmpty();
    }

    @Test
    void when_find_hospital_by_id_then_return_empty() {
        var hospitalOptional = hospitalRepo.findByHospitalName("$5^");
        assertThat(hospitalOptional).isEmpty();
    }

    @Test
    void when_search_hospital_return_success() {
        var hospital = newHospital();
        hospitalRepo.save(hospital);
        var pageRequest = of(0, 50);
        var searchDtoList = hospitalRepo.searchByHospitalNameContaining("ho", pageRequest);
        assertThat(searchDtoList).isNotEmpty();
    }

    @Test
    void when_get_hospital_by_id_then_return_success() {
        var hospital = newHospital();
        hospitalRepo.save(hospital);
        var hospitalOptional = hospitalRepo.getHospitalById(hospital.getId());
        assertThat(hospitalOptional).isNotEmpty();
    }
    @Test
    void when_get_all_hospital_then_return_success() {
        var hospital = newHospital();
        hospitalRepo.save(hospital);
        var pageRequest = of(0, 50);
        var hospitalOptional = hospitalRepo.getAllHospital(pageRequest);
        assertThat(hospitalOptional).isNotEmpty();
    }

    private Hospital newHospital() {
        var address = new Address("addressLine1", "addressLine2", "state",
                "country", "pinCode");
        var common = new Common();
        common.setCreatedBy(1L);
        common.setModifiedBy(1L);
        common.setCreatedDate(LocalDateTime.now());
        common.setModifiedDate(LocalDateTime.now());
        common.setFlag(true);
        common.setStatus(true);
        Hospital hospital = new Hospital();
        hospital.setHospitalName("AK hospital");
        hospital.setFoundedAt("1989");
        hospital.setAddress(address);
        hospital.setCommon(common);
        return hospital;
    }
}
