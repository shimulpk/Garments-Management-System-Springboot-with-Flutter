package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.response.DashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.*;
import com.emranhss.GarmentsManagementSystem.enums.*;
import com.emranhss.GarmentsManagementSystem.repository.*;
import com.emranhss.GarmentsManagementSystem.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.emranhss.GarmentsManagementSystem.dto.response.OrderTrackingResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {


    private final UserRepository userRepository;
    private final BuyerRepository buyerRepository;
    private final VendorRepository vendorRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final ShipmentRepository shipmentRepository;
    private final StoreRequisitionRepository storeRequisitionRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final StockRepository stockRepository;
    private final DayWiseCuttingProductionRepository cuttingRepository;
    private final DayWiseSewingProductionRepository sewingRepository;
    private final DayWiseFinishingProductionRepository finishingRepository;
    private final DayWisePackingProductionRepository packingRepository;

    private final CuttingPlanRepository cuttingPlanRepository;

    private final SewingPlanRepository sewingPlanRepository;

    private final FinishingPlanRepository finishingPlanRepository;

    private final PackingPlanRepository packingPlanRepository;


    @Override
    public DashboardResponseDto getDashboard() {
        DashboardResponseDto dto = new DashboardResponseDto();

        // ===============================
        // Master Counts
        // ===============================

        dto.setTotalUsers(userRepository.count());

        dto.setTotalBuyers(buyerRepository.count());

        dto.setTotalVendors(vendorRepository.count());

        dto.setTotalItems(itemRepository.count());

        dto.setTotalOrders(orderRepository.count());
        dto.setTotalShipments(shipmentRepository.count());

        // ===============================
        // Pending
        // ===============================

        dto.setPendingRequisitions(
                (long) storeRequisitionRepository
                        .findByStatus(StoreRequisitionStatus.PENDING)
                        .size()
        );

        dto.setPendingPurchaseOrders(
                (long) purchaseOrderRepository
                        .findAllByStatus(PurchaseOrderStatus.PENDING)
                        .size()
        );

        // ===============================
        // Stock
        // ===============================

        dto.setCurrentStockItems(
                (long) stockRepository
                        .findByAvailableQuantityGreaterThan(0D)
                        .size()
        );

        // ===============================
        // Today's Production
        // ===============================

        dto.setTodayCuttingPcs(
                cuttingRepository
                        .getTodayCutting(LocalDate.now())
                        .longValue()
        );

        dto.setTodaySewingPcs(
                sewingRepository
                        .getTodaySewing(LocalDate.now())
                        .longValue()
        );

        dto.setTodayFinishingPcs(
                finishingRepository
                        .getTodayFinishing(LocalDate.now())
                        .longValue()
        );



        dto.setTodayPackingPcs(
                packingRepository
                        .getTodayPacking(LocalDate.now())
                        .longValue()
        );

        // ===============================
        // Order Tracking
        // ===============================

        List<OrderTrackingResponseDto> trackingList =
                orderRepository.findAllByOrderByShipDateAsc()
                        .stream()
                        .map(order -> {

                            OrderTrackingResponseDto tracking =
                                    new OrderTrackingResponseDto();

                            tracking.setOrderNumber(order.getOrderId());

                            tracking.setBuyerName(
                                    order.getBuyer().getBuyerName());

                            tracking.setStyleName(
                                    order.getStyle().getStyleCode());

                            tracking.setOrderQuantity(
                                    order.getTotalQuantity());

                            String stage = getCurrentStage(order);

                            tracking.setCurrentStage(stage);

                            tracking.setDeliveryDeadline(
                                    order.getShipDate());

                            tracking.setProgress(getProgress(stage));

                            return tracking;

                        })
                        .collect(Collectors.toList());

        dto.setOrderTracking(trackingList);

        return dto;
    }

    private Integer getProgress(String stage) {

        if (stage == null) return 0;

        return switch (stage) {

            case "PLANNING" -> 10;

            case "CUTTING" -> 30;

            case "SEWING" -> 55;

            case "FINISHING" -> 80;

            case "PACKING" -> 90;

            case "READY TO SHIP" -> 100;

            default -> 0;
        };
    }



    private String getCurrentStage(Order order) {

        Optional<CuttingPlan> cutting =
                cuttingPlanRepository.findByOrder_Id(order.getId());

        if (cutting.isEmpty()) {
            return "PLANNING";
        }

        if (cutting.get().getStatus() != CuttingPlanStatus.COMPLETED) {
            return "CUTTING";
        }

        Optional<SewingPlan> sewing =
                sewingPlanRepository.findByCuttingPlan_Id(
                        cutting.get().getId());

        if (sewing.isEmpty()) {
            return "SEWING";
        }

        if (sewing.get().getStatus() != SewingPlanStatus.COMPLETED) {
            return "SEWING";
        }

        Optional<FinishingPlan> finishing =
                finishingPlanRepository.findBySewingPlan_Id((
                        sewing.get().getId()));

        if (finishing.isEmpty()) {
            return "FINISHING";
        }

        if (finishing.get().getStatus() != FinishingPlanStatus.COMPLETED) {
            return "FINISHING";
        }

        Optional<PackingPlan> packing =
                packingPlanRepository.findByFinishingPlan_Id((
                        finishing.get().getId()));

        if (packing.isEmpty()) {
            return "PACKING";
        }

        if (packing.get().getStatus() != PackingPlanStatus.READY_TO_SHIP) {
            return "PACKING";
        }

        return "READY TO SHIP";
    }

    
    }

