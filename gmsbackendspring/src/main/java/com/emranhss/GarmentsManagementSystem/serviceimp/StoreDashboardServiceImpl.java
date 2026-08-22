package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.response.LowStockResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.RecentGrnResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.RecentMaterialIssueResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.StoreDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.GoodsReceiveNote;
import com.emranhss.GarmentsManagementSystem.entity.MaterialIssue;
import com.emranhss.GarmentsManagementSystem.entity.Stock;
import com.emranhss.GarmentsManagementSystem.enums.StoreRequisitionStatus;
import com.emranhss.GarmentsManagementSystem.repository.*;
import com.emranhss.GarmentsManagementSystem.service.StoreDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreDashboardServiceImpl implements StoreDashboardService {

    private final ItemRepository itemRepository;
    private final StockRepository stockRepository;
    private final StoreRequisitionRepository storeRequisitionRepository;
    private final GoodsReceiveNoteRepository goodsReceiveNoteRepository;
    private final MaterialIssueRepository materialIssueRepository;

    @Override
    public StoreDashboardResponseDto getDashboard() {
        StoreDashboardResponseDto dto = new StoreDashboardResponseDto();

        // ===============================
        // Summary
        // ===============================

        dto.setTotalItems(itemRepository.count());

        dto.setCurrentStockItems(
                stockRepository.countByAvailableQuantityGreaterThan(0D)
        );

        dto.setLowStockItems(
                stockRepository.countByAvailableQuantityLessThanEqual(10D)
        );

        dto.setPendingStoreRequisitions(
                storeRequisitionRepository.countByStatus(StoreRequisitionStatus.PENDING)
        );

        dto.setTodayGoodsReceive(
                goodsReceiveNoteRepository.countByGrnDate(LocalDate.now())
        );

        dto.setTodayMaterialIssue(
                materialIssueRepository.countByIssueDate(LocalDate.now())
        );

        // ===============================
        // Recent GRN
        // ===============================

        dto.setRecentGoodsReceives(

                goodsReceiveNoteRepository
                        .findTop5ByOrderByGrnDateDesc()
                        .stream()
                        .map(this::mapGrn)
                        .collect(Collectors.toList())

        );

        // ===============================
        // Recent Material Issue
        // ===============================

        dto.setRecentMaterialIssues(

                materialIssueRepository
                        .findTop5ByOrderByIssueDateDesc()
                        .stream()
                        .map(this::mapIssue)
                        .collect(Collectors.toList())

        );

        // ===============================
        // Low Stock
        // ===============================

        dto.setLowStockList(

                stockRepository
                        .findTop5ByAvailableQuantityLessThanOrderByAvailableQuantityAsc(10D)
                        .stream()
                        .map(this::mapStock)
                        .collect(Collectors.toList())

        );

        return dto;
    }

    // =======================================================
    // Mapper
    // =======================================================

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

    private RecentMaterialIssueResponseDto mapIssue(MaterialIssue issue) {

        return new RecentMaterialIssueResponseDto(

                issue.getIssueNo(),

                issue.getIssueDate(),

                issue.getDepartment(),

                issue.getRequestedBy(),

                issue.getStatus()

        );

    }

    private LowStockResponseDto mapStock(Stock stock) {

        return new LowStockResponseDto(

                stock.getItem().getItemName(),

                stock.getItem().getCategory(),

                stock.getItem().getUnit(),

                stock.getAvailableQuantity()

        );


    }
}
