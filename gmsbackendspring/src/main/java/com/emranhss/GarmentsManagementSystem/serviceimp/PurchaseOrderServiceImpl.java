package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.PurchaseOrderMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.PurchaseOrderItemRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.request.PurchaseOrderRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.PurchaseOrderResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.*;
import com.emranhss.GarmentsManagementSystem.enums.PurchaseOrderStatus;
import com.emranhss.GarmentsManagementSystem.enums.StoreRequisitionStatus;
import com.emranhss.GarmentsManagementSystem.repository.*;
import com.emranhss.GarmentsManagementSystem.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    private final PurchaseOrderItemRepository purchaseOrderItemRepository;

    private final VendorRepository vendorRepository;

    private final StoreRequisitionRepository storeRequisitionRepository;

    private final ItemRepository itemRepository;


    @Override
    public PurchaseOrderResponseDto create(PurchaseOrderRequestDto request) {
        Vendor vendor =
                vendorRepository.findById(request.getVendorId())
                        .orElseThrow(() ->
                                new RuntimeException("Vendor Not Found"));

        StoreRequisition requisition =
                storeRequisitionRepository
                        .findById(request.getStoreRequisitionId())
                        .orElseThrow(() ->
                                new RuntimeException("Store Requisition Not Found"));

        // Only APPROVED requisition can create PO

        if (requisition.getStatus()
                != StoreRequisitionStatus.APPROVED) {

            throw new RuntimeException(
                    "Store Requisition is not approved.");
        }

        if (purchaseOrderRepository.existsByStoreRequisitionId(requisition.getId())) {
            throw new RuntimeException(
                    "Purchase Order already exists for this Store Requisition.");
        }

        PurchaseOrder purchaseOrder =
                new PurchaseOrder();

        purchaseOrder.setPoNo(
                generatePoNo());

        purchaseOrder.setPoDate(
                request.getPoDate());

        purchaseOrder.setVendor(
                vendor);

        purchaseOrder.setStoreRequisition(
                requisition);

        purchaseOrder.setStatus(
                PurchaseOrderStatus.PENDING);

        purchaseOrder.setRemarks(
                request.getRemarks());

        List<PurchaseOrderItem> items =
                buildPurchaseOrderItems(
                        purchaseOrder,
                        requisition,
                        request.getItems());

        purchaseOrder.setItems(items);

        purchaseOrder.setGrandTotal(
                calculateGrandTotal(items));

        PurchaseOrder saved =
                purchaseOrderRepository.save(
                        purchaseOrder);

        // Store Requisition status update
        requisition.setStatus(
                StoreRequisitionStatus.PO_CREATED);

        storeRequisitionRepository.save(
                requisition);
        return PurchaseOrderMapper.toDto(saved);
    }

    @Override
    public PurchaseOrderResponseDto update(Long id, PurchaseOrderRequestDto request) {
        PurchaseOrder purchaseOrder =
                purchaseOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Purchase Order Not Found"));

        Vendor vendor =
                vendorRepository.findById(request.getVendorId())
                        .orElseThrow(() ->
                                new RuntimeException("Vendor Not Found"));

        StoreRequisition requisition =
                storeRequisitionRepository
                        .findById(request.getStoreRequisitionId())
                        .orElseThrow(() ->
                                new RuntimeException("Store Requisition Not Found"));

        if (requisition.getStatus()
                != StoreRequisitionStatus.APPROVED) {

            throw new RuntimeException(
                    "Store Requisition is not approved.");
        }

        purchaseOrder.setPoDate(
                request.getPoDate());

        purchaseOrder.setVendor(
                vendor);

        purchaseOrder.setStoreRequisition(
                requisition);

        purchaseOrder.setRemarks(
                request.getRemarks());

        purchaseOrder.getItems().clear();

        List<PurchaseOrderItem> items =
                buildPurchaseOrderItems(
                        purchaseOrder,
                        requisition,
                        request.getItems());

        purchaseOrder.getItems().addAll(items);

        purchaseOrder.setGrandTotal(
                calculateGrandTotal(items));

        PurchaseOrder updated =
                purchaseOrderRepository.save(
                        purchaseOrder);

        return PurchaseOrderMapper.toDto(updated);
    }

    @Override
    public PurchaseOrderResponseDto getById(Long id) {
        PurchaseOrder purchaseOrder =
                purchaseOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Purchase Order Not Found"));

        return PurchaseOrderMapper.toDto(
                purchaseOrder);
    }

    @Override
    public List<PurchaseOrderResponseDto> getAll() {
        return purchaseOrderRepository
                .findAll()
                .stream()
                .map(PurchaseOrderMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        PurchaseOrder purchaseOrder =
                purchaseOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Purchase Order Not Found"));

        purchaseOrderRepository.delete(
                purchaseOrder);
    }

    @Override
    public List<PurchaseOrderResponseDto> getPendingPurchaseOrders() {
        return purchaseOrderRepository
                .findAllByStatus(PurchaseOrderStatus.PENDING)
                .stream()
                .map(PurchaseOrderMapper::toDto)
                .toList();
    }


    // =====================================================
    // Generate PO Number
    // =====================================================

    private String generatePoNo() {

        long count =
                purchaseOrderRepository.count() + 1;

        return String.format(
                "PO-%04d",
                count
        );

    }

    // =====================================================
// Build Purchase Order Items
// =====================================================

    private List<PurchaseOrderItem> buildPurchaseOrderItems(

            PurchaseOrder purchaseOrder,

            StoreRequisition storeRequisition,

            List<PurchaseOrderItemRequestDto> requestItems) {

        return requestItems.stream().map(dto -> {

            StoreRequisitionItem requisitionItem =
                    storeRequisition.getItems()
                            .stream()
                            .filter(item ->
                                    item.getItem().getId()
                                            .equals(dto.getItemId()))
                            .findFirst()
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Item not found in Store Requisition"));

            PurchaseOrderItem poItem =
                    new PurchaseOrderItem();

            poItem.setPurchaseOrder(
                    purchaseOrder);

            poItem.setItem(
                    requisitionItem.getItem());

            // Quantity always comes from Store Requisition
            poItem.setQuantity(
                    requisitionItem.getQuantity());

            // Procurement only enters Unit Price
            poItem.setUnitPrice(
                    dto.getUnitPrice());

            poItem.setLineTotal(
                    requisitionItem.getQuantity()
                            * dto.getUnitPrice());

            return poItem;

        }).toList();

    }

    // =====================================================
    // Calculate Grand Total
    // =====================================================

    private Double calculateGrandTotal(
            List<PurchaseOrderItem> items) {

        return items
                .stream()
                .mapToDouble(
                        PurchaseOrderItem::getLineTotal)
                .sum();

    }

}


