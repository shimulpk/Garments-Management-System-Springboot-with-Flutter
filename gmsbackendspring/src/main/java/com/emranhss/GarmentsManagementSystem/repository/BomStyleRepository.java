package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.entity.BomStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BomStyleRepository extends JpaRepository<BomStyle,Long> {

    List<BomStyle> findByBuyerId(Long buyerId);
}
