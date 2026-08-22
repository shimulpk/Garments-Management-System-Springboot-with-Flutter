package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.entity.RmcCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RmcCheckRepository extends JpaRepository<RmcCheck,  Long> {

    boolean existsByOrderId(Long orderId);
}
