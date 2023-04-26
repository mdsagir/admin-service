package com.doctory.infra.repo;

import com.doctory.domain.hospital.dto.HospitalDto;
import com.doctory.domain.SearchDto;
import com.doctory.infra.entity.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HospitalRepo extends JpaRepository<Hospital, Long> {

    Optional<Hospital> findByHospitalName(String hospitalName);

    @Query("""
            SELECT new com.doctory.domain.SearchDto(h.id,h.hospitalName)\s
            FROM Hospital h where LOWER(h.hospitalName) LIKE LOWER (concat('%',:hospitalName,'%'))
            """)
    List<SearchDto> searchByHospitalNameContaining(String hospitalName, Pageable pageable);


    @Query("SELECT h FROM Hospital h LEFT JOIN FETCH h.address where h.id =:id")
    Optional<Hospital> getHospitalById(Long id);

    @Query(value = """
            SELECT new com.doctory.domain.hospital.dto.HospitalDto(h) FROM Hospital h LEFT JOIN FETCH h.address\s
            ORDER BY h.common.createdDate desc\s
            """, countQuery = "SELECT COUNT(h.id) FROM Hospital h")
    Page<HospitalDto> getAllHospital(Pageable pageable);
}
