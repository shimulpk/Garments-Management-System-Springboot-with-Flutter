package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.entity.FabricsCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FabricsCheckRepository extends JpaRepository<FabricsCheck, Long> {
    Optional<FabricsCheck> findByOrder_Id(Long orderId);

    boolean existsByOrderId(Long orderId);


}
