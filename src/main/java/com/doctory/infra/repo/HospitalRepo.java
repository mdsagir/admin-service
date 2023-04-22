package com.doctory.infra.repo;

import com.doctory.domain.hospital.dto.HospitalDto;
import com.doctory.domain.hospital.dto.HospitalSearchDto;
import com.doctory.infra.entity.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface HospitalRepo extends JpaRepository<Hospital,Long> {

    Optional<Hospital> findByHospitalName(String hospitalName);

    @Query("""
        SELECT new com.doctory.domain.hospital.dto.HospitalSearchDto(h.id,h.hospitalName) FROM Hospital h where LOWER(h.hospitalName) 
        LIKE LOWER (concat('%',:hospitalName,'%'))
        """)
    List<HospitalSearchDto> searchByHospitalNameContaining(String hospitalName, Pageable pageable);


    @Query("SELECT h FROM Hospital h LEFT JOIN FETCH h.address where h.id =:id")
    Optional<Hospital> getHospitalById(Long id);

    @Query(value = "SELECT new com.doctory.domain.hospital.dto.HospitalDto(h) FROM Hospital h LEFT JOIN FETCH h.address ORDER BY h.common.createdBy DESC "
    ,countQuery = "SELECT h.id FROM Hospital h ORDER BY h.common.createdBy DESC")
    Page<HospitalDto> getAllHospital(Pageable pageable);
}
