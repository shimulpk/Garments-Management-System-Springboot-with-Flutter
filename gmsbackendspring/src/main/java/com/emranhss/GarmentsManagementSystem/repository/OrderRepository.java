package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyerId(Long buyerId);

    List<Order> findTop5ByOrderByIdDesc();

    long countByStatus(String status);

    List<Order> findAllByOrderByShipDateAsc();

    @Query("""
SELECT o
FROM Order o
WHERE o.buyer.id = :buyerId
AND o.id NOT IN (
    SELECT cp.order.id
    FROM CuttingPlan cp
)
""")
    List<Order> findAvailableOrdersByBuyer(Long buyerId);



}
