package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.entity.CuttingPlan;
import com.emranhss.GarmentsManagementSystem.enums.CuttingPlanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuttingPlanRepository extends JpaRepository<CuttingPlan, Long> {
    Optional<CuttingPlan> findByOrder_Id(Long orderId);

    boolean existsByOrder_Id(Long orderId);

    List<CuttingPlan> findByStatus(CuttingPlanStatus status);

    long countByStatus(CuttingPlanStatus status);

    @Query("""
select cp
from CuttingPlan cp
order by cp.startDate desc
""")
    List<CuttingPlan> findTop5ByOrderByStartDateDesc();


}
