package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.response.PendingRequisitionResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.PurchaseDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.RecentGrnResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.RecentPurchaseOrderResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.GoodsReceiveNote;
import com.emranhss.GarmentsManagementSystem.entity.PurchaseOrder;
import com.emranhss.GarmentsManagementSystem.entity.StoreRequisition;
import com.emranhss.GarmentsManagementSystem.enums.PurchaseOrderStatus;
import com.emranhss.GarmentsManagementSystem.enums.StoreRequisitionStatus;
import com.emranhss.GarmentsManagementSystem.repository.GoodsReceiveNoteRepository;
import com.emranhss.GarmentsManagementSystem.repository.PurchaseOrderRepository;
import com.emranhss.GarmentsManagementSystem.repository.StoreRequisitionRepository;
import com.emranhss.GarmentsManagementSystem.repository.VendorRepository;
import com.emranhss.GarmentsManagementSystem.service.PurchaseDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseDashboardServiceImpl implements PurchaseDashboardService {

    private final VendorRepository vendorRepository;
    private final StoreRequisitionRepository storeRequisitionRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final GoodsReceiveNoteRepository goodsReceiveNoteRepository;


    @Override
    public PurchaseDashboardResponseDto getDashboard() {
        PurchaseDashboardResponseDto dto = new PurchaseDashboardResponseDto();

        // =========================================
        // Summary
        // =========================================

        dto.setTotalVendors(
                vendorRepository.count()
        );

        dto.setPendingRequisitions(
                storeRequisitionRepository.countByStatus(
                        StoreRequisitionStatus.PENDING
                )
        );

        dto.setPendingPurchaseOrders(
                purchaseOrderRepository.countByStatus(
                        PurchaseOrderStatus.PENDING
                )
        );

        dto.setTodayPurchaseOrders(
                purchaseOrderRepository.countByPoDate(
                        LocalDate.now()
                )
        );

        dto.setTodayGoodsReceive(
                goodsReceiveNoteRepository.countByGrnDate(
                        LocalDate.now()
                )
        );

        // =========================================
        // Recent Purchase Orders
        // =========================================

        dto.setRecentPurchaseOrders(

                purchaseOrderRepository
                        .findTop5ByOrderByPoDateDesc()
                        .stream()
                        .map(this::mapPurchaseOrder)
                        .collect(Collectors.toList())

        );

        // =========================================
        // Pending Store Requisitions
        // =========================================

        dto.setPendingRequisitionsList(

                storeRequisitionRepository
                        .findTop5ByStatusOrderByRequisitionDateDesc(
                                StoreRequisitionStatus.PENDING
                        )
                        .stream()
                        .map(this::mapStoreRequisition)
                        .collect(Collectors.toList())

        );

        // =========================================
        // Recent GRN
        // =========================================

        dto.setRecentGoodsReceives(

                goodsReceiveNoteRepository
                        .findTop5ByOrderByGrnDateDesc()
                        .stream()
                        .map(this::mapGrn)
                        .collect(Collectors.toList())

        );

        return dto;

    }

    // ======================================================
    // Mapper
    // ======================================================

    private RecentPurchaseOrderResponseDto mapPurchaseOrder(PurchaseOrder po) {

        return new RecentPurchaseOrderResponseDto(

                po.getPoNo(),

                po.getPoDate(),

                po.getVendor().getCompanyName(),

                po.getGrandTotal(),

                po.getStatus().name()

        );

    }

    private PendingRequisitionResponseDto mapStoreRequisition(StoreRequisition sr) {

        return new PendingRequisitionResponseDto(

                sr.getPrNo(),

                sr.getRequisitionDate(),

                sr.getRequestedBy(),

                sr.getDepartment()

        );

    }

    private RecentGrnResponseDto mapGrn(GoodsReceiveNote grn) {

        return new RecentGrnResponseDto(

                grn.getGrnNo(),

                grn.getGrnDate(),

                grn.getPurchaseOrder()
                        .getVendor()
                        .getCompanyName(),

                grn.getGrandTotal()

        );

    }

}

