package com.doctory.infra.repo;

import com.doctory.infra.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepo extends JpaRepository<Hospital,Long> {

    Optional<Hospital> findByHospitalName(String hospitalName);
}
