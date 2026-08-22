package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.entity.Stock;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByItemId(Long itemId);
    List<Stock> findByAvailableQuantityGreaterThan(Double quantity);

    List<Stock> findTop5ByAvailableQuantityLessThanOrderByAvailableQuantityAsc(Double quantity);


    long countByAvailableQuantityGreaterThan(Double quantity);

    long countByAvailableQuantityLessThanEqual(Double quantity);

}
