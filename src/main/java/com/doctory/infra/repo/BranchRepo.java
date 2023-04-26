package com.doctory.infra.repo;

import com.doctory.infra.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BranchRepo extends JpaRepository<Branch,Long> {


    @Query("SELECT b FROM Branch b LEFT JOIN FETCH b.address where b.id =:id")
    Optional<Branch> findBranchById(Long id);
}
