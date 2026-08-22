package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.response.MerchandiserDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.MerchandiserOrderResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Order;
import com.emranhss.GarmentsManagementSystem.repository.*;
import com.emranhss.GarmentsManagementSystem.service.MerchandiserDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MerchandiserDashboardServiceImpl implements MerchandiserDashboardService {

    private final BuyerRepository buyerRepository;
    private final BomStyleRepository bomStyleRepository;
    private final OrderRepository orderRepository;
    private final BomViewRepository bomViewRepository;
    private final RmcCheckRepository rmcCheckRepository;
    private final FabricsCheckRepository fabricsCheckRepository;

    @Override
    public MerchandiserDashboardResponseDto getDashboard() {
        MerchandiserDashboardResponseDto dto =
                new MerchandiserDashboardResponseDto();

        // ===========================================
        // Summary Cards
        // ===========================================

        dto.setTotalBuyers(
                buyerRepository.count()
        );

        dto.setTotalStyles(
                bomStyleRepository.count()
        );

        dto.setTotalOrders(
                orderRepository.count()
        );

        dto.setTotalBom(
                bomViewRepository.count()
        );

        dto.setTotalRawMaterialChecks(
                rmcCheckRepository.count()
        );

        dto.setTotalFabricChecks(
                fabricsCheckRepository.count()
        );

        // ===========================================
        // Recent Orders
        // ===========================================

        List<MerchandiserOrderResponseDto> recentOrders =
                orderRepository.findTop5ByOrderByIdDesc()
                        .stream()
                        .map(this::mapOrder)
                        .collect(Collectors.toList());

        dto.setRecentOrders(recentOrders);

        return dto;
    }

    // ===========================================
    // Mapper
    // ===========================================

    private MerchandiserOrderResponseDto mapOrder(Order order) {

        MerchandiserOrderResponseDto dto =
                new MerchandiserOrderResponseDto();

        dto.setOrderNumber(order.getOrderId());

        dto.setBuyerName(
                order.getBuyer().getBuyerName()
        );

        dto.setStyleName(
                order.getStyle().getStyleName()
        );

        dto.setOrderQuantity(
                order.getTotalQuantity()
        );

        dto.setShipDate(
                order.getShipDate()
        );

        dto.setStatus(
                order.getStatus()
        );

        return dto;
    }

}

