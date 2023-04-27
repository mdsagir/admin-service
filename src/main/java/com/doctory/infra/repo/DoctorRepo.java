package com.doctory.infra.repo;

import com.doctory.domain.SearchDto;
import com.doctory.domain.doctor.dto.DoctorDto;
import com.doctory.infra.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorRepo extends JpaRepository<Doctor, Long> {


    Optional<Doctor> findByPerson_FirstName(String firstName);

    @Query("""
            SELECT new com.doctory.domain.SearchDto(d.id,d.person.firstName)\s
            FROM Doctor d where LOWER(d.person.firstName) LIKE LOWER (concat('%',:doctorName,'%'))
            """)
    List<SearchDto> searchByDoctorNameContaining(String doctorName, Pageable pageable);

    @Query("SELECT d FROM Doctor d LEFT JOIN FETCH d.person LEFT JOIN FETCH d.branch where d.id =:id")
    Optional<Doctor> getDoctorById(Long id);

    @Query(value = """
            SELECT new com.doctory.domain.doctor.dto.DoctorDto(d) FROM Doctor d LEFT JOIN FETCH d.person LEFT JOIN FETCH d.branch\s
            ORDER BY d.common.createdDate desc\s
            """, countQuery = "SELECT COUNT(d.id) FROM Doctor d")
    Page<DoctorDto> getAllDoctor(Pageable pageable);
}
