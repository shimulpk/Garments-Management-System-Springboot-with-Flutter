package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.entity.StoreRequisition;
import com.emranhss.GarmentsManagementSystem.enums.StoreRequisitionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface StoreRequisitionRepository extends JpaRepository<StoreRequisition, Long> {
    boolean existsByPrNo(String prNo);
    List<StoreRequisition> findByStatus(StoreRequisitionStatus status);

    long countByStatus(StoreRequisitionStatus status);

    List<StoreRequisition> findTop5ByStatusOrderByRequisitionDateDesc(
            StoreRequisitionStatus status
    );



}
