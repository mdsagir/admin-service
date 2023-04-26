package com.doctory.infra.repo;

import com.doctory.domain.SearchDto;
import com.doctory.domain.branch.dto.BranchDto;
import com.doctory.infra.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BranchRepo extends JpaRepository<Branch,Long> {


    @Query("SELECT b FROM Branch b LEFT JOIN FETCH b.address where b.id =:id")
    Optional<Branch> findBranchById(Long id);

    @Query("""
            SELECT new com.doctory.domain.SearchDto(b.id,b.branchName)\s
            FROM Branch b where LOWER(b.branchName) LIKE LOWER (concat('%',:branchName,'%'))
            """)
    List<SearchDto> searchByBranchNameContaining(String branchName, Pageable pageable);

    @Query(value = """
            SELECT new com.doctory.domain.branch.dto.BranchDto(b) FROM Branch b LEFT JOIN FETCH b.address\s
            ORDER BY b.common.createdDate desc\s
            """, countQuery = "SELECT COUNT(b.id) FROM Branch b")
    Page<BranchDto> getAllBranch(Pageable pageable);
}
