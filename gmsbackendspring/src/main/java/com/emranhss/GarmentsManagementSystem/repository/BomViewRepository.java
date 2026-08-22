package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.entity.BomView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface BomViewRepository extends JpaRepository<BomView,Long> {

    List<BomView> findByStyleId(Long styleId);



}
