package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.entity.Shipment;
import com.emranhss.GarmentsManagementSystem.enums.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment,Long> {

    // Shipment No Check
    Optional<Shipment> findByShipmentNo(String shipmentNo);

    // Same Packing Plan can not create Shipment twice
    boolean existsByPackingPlanId(Long packingPlanId);

    // Shipment Status Wise List
    List<Shipment> findByStatus(ShipmentStatus status);

    // Dashboard
    long countByStatus(ShipmentStatus status);

    // Dashboard - Today's Shipments
    long countByShipmentDate(LocalDate shipmentDate);

    // Latest Shipment
    List<Shipment> findAllByOrderByShipmentDateDesc();

    // Dashboard - Recent Shipments
    List<Shipment> findTop5ByOrderByShipmentDateDesc();


}
