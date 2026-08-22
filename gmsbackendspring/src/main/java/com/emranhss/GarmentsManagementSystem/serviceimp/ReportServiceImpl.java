package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.response.DashboardReportResponseDto;
import com.emranhss.GarmentsManagementSystem.repository.*;
import com.emranhss.GarmentsManagementSystem.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final BuyerRepository buyerRepository;

    private final BomStyleRepository bomStyleRepository;

    private final OrderRepository orderRepository;

    private final VendorRepository vendorRepository;

    private final PurchaseOrderRepository purchaseOrderRepository;

    private final ItemRepository itemRepository;

    private final GoodsReceiveNoteRepository goodsReceiveNoteRepository;

    private final MaterialIssueRepository materialIssueRepository;

    private final ProductionLineRepository productionLineRepository;

    private final MachineRepository machineRepository;

    private final PackingPlanRepository packingPlanRepository;

    private final ShipmentRepository shipmentRepository;

    private final DayWiseCuttingProductionRepository dayWiseCuttingProductionRepository;

    private final DayWiseSewingProductionRepository dayWiseSewingProductionRepository;

    private final DayWiseFinishingProductionRepository dayWiseFinishingProductionRepository;

    private final DayWisePackingProductionRepository dayWisePackingProductionRepository;

    @Override
    public DashboardReportResponseDto getDashboardReport() {
        DashboardReportResponseDto dto =
                new DashboardReportResponseDto();

        // ==========================
        // Merchandising
        // ==========================

        dto.setTotalBuyers(

                buyerRepository.count()

        );

        dto.setTotalStyles(

                bomStyleRepository.count()

        );

        dto.setTotalOrders(

                orderRepository.count()

        );

        // ==========================
        // Procurement
        // ==========================

        dto.setTotalVendors(

                vendorRepository.count()

        );

        dto.setTotalPurchaseOrders(

                purchaseOrderRepository.count()

        );

        // ==========================
        // Inventory
        // ==========================

        dto.setTotalItems(

                itemRepository.count()

        );

        dto.setTotalGrns(

                goodsReceiveNoteRepository.count()

        );

        dto.setTotalMaterialIssues(

                materialIssueRepository.count()

        );

        // ==========================
        // Production
        // ==========================

        dto.setTotalProductionLines(

                productionLineRepository.count()

        );

        dto.setTotalMachines(

                machineRepository.count()

        );

        dto.setTotalPackingPlans(

                packingPlanRepository.count()

        );

        // ==========================
        // Shipment
        // ==========================

        dto.setTotalShipments(

                shipmentRepository.count()

        );


        dto.setTotalCuttingProduction(

                dayWiseCuttingProductionRepository
                        .getTotalProduction()

        );

        dto.setTotalSewingProduction(

                dayWiseSewingProductionRepository
                        .getTotalProduction()

        );

        dto.setTotalFinishingProduction(

                dayWiseFinishingProductionRepository
                        .getTotalProduction()

        );

        dto.setTotalPackingProduction(

                dayWisePackingProductionRepository
                        .getTotalProduction()

        );

        return dto;

    }


}
