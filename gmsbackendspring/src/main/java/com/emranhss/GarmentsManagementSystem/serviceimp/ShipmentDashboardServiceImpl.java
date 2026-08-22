package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.response.RecentShipmentResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.ShipmentDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Shipment;
import com.emranhss.GarmentsManagementSystem.enums.ShipmentStatus;
import com.emranhss.GarmentsManagementSystem.repository.ShipmentRepository;
import com.emranhss.GarmentsManagementSystem.service.ShipmentDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentDashboardServiceImpl implements ShipmentDashboardService {

    private final ShipmentRepository shipmentRepository;

    @Override
    public ShipmentDashboardResponseDto getDashboard() {

        ShipmentDashboardResponseDto dto =
                new ShipmentDashboardResponseDto();

        // =====================================
        // Summary
        // =====================================

        dto.setTodayShipments(

                shipmentRepository
                        .countByShipmentDate(LocalDate.now())

        );

        dto.setTotalShipments(

                shipmentRepository.count()

        );

        dto.setPendingShipments(

                shipmentRepository
                        .countByStatus(ShipmentStatus.PENDING)

        );

        dto.setShippedShipments(

                shipmentRepository
                        .countByStatus(ShipmentStatus.SHIPPED)

        );

        // =====================================
        // Recent Shipments
        // =====================================

        List<RecentShipmentResponseDto> recentShipments =
                new ArrayList<>();

        List<Shipment> shipments =
                shipmentRepository
                        .findTop5ByOrderByShipmentDateDesc();

        for (Shipment shipment : shipments) {

            recentShipments.add(

                    new RecentShipmentResponseDto(

                            shipment.getShipmentNo(),

                            shipment.getStyleNo(),

                            shipment.getBuyerName(),

                            shipment.getDestination(),

                            shipment.getShipmentQty(),

                            shipment.getShipmentDate(),

                            shipment.getStatus()

                    )

            );

        }

        dto.setRecentShipments(recentShipments);

        return dto;

    }

}
