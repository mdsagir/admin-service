package com.doctory.jpa.hospital;

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

@DataJpaTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class HospitalJpaTest {


    @Autowired
    private HospitalRepo hospitalRepo;


    @Test
    void when_save_and_update_doctor_then_return_success() {

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
        hospital.setHospitalName("AK");
        hospital.setFoundedAt("1989");
        hospital.setAddress(address);
        hospital.setCommon(common);
        hospitalRepo.save(hospital);

        var all = hospitalRepo.findAll();
        assertThat(all).hasSize(1);

    }
}
