package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.entity.PurchaseOrder;
import com.emranhss.GarmentsManagementSystem.enums.PurchaseOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    Optional<PurchaseOrder> findByPoNo(String poNo);
    Optional<PurchaseOrder> findById(Long id);
    List<PurchaseOrder> findAllByStatus(PurchaseOrderStatus status);
    boolean existsByStoreRequisitionId(Long storeRequisitionId);

   

    long countByStatus(PurchaseOrderStatus status);

    long countByPoDate(LocalDate poDate);

    List<PurchaseOrder> findTop5ByOrderByPoDateDesc();


}
