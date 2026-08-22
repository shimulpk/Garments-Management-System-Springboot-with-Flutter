package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.entity.GoodsReceiveNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GoodsReceiveNoteRepository extends JpaRepository<GoodsReceiveNote, Long> {
    Optional<GoodsReceiveNote> findTopByOrderByIdDesc();

    boolean existsByPurchaseOrderId(Long purchaseOrderId);

    List<GoodsReceiveNote> findTop5ByOrderByGrnDateDesc();

    long countByGrnDate(LocalDate grnDate);

    
}
