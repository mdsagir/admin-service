package com.doctory.infra.repo;

import com.doctory.infra.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepo extends JpaRepository<Branch,Long> {
}
