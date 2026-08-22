package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.entity.FinishingPlan;
import com.emranhss.GarmentsManagementSystem.entity.PackingPlan;
import com.emranhss.GarmentsManagementSystem.enums.FinishingPlanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinishingPlanRepository extends JpaRepository<FinishingPlan, Long> {

//     Find by Finishing Plan Code

    Optional<FinishingPlan> findByFinishingPlanId(
            String finishingPlanId);



//    Find by Status

    List<FinishingPlan> findByStatus(
            FinishingPlanStatus status);

    long countByStatus(FinishingPlanStatus status);

    List<FinishingPlan> findTop5ByOrderByStartDateDesc();

    Optional<FinishingPlan> findBySewingPlan_Id(Long sewingPlanId);

    boolean existsBySewingPlanId(Long sewingPlanId);
}
