package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.entity.PackingPlan;
import com.emranhss.GarmentsManagementSystem.enums.PackingPlanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PackingPlanRepository extends JpaRepository<PackingPlan, Long> {

    long countByStatus(PackingPlanStatus status);

    List<PackingPlan> findTop5ByOrderByStartDateDesc();

    Optional<PackingPlan> findByFinishingPlan_Id(Long finishingPlanId);

    boolean existsByFinishingPlanId(Long finishingPlanId);

    List<PackingPlan> findByStatus(PackingPlanStatus status);

    boolean existsById(Long id);



}
