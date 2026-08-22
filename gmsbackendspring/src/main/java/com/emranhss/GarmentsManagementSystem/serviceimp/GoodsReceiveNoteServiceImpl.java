package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.GoodsReceiveNoteMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.GoodsReceiveNoteRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.GoodsReceiveNoteResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.*;
import com.emranhss.GarmentsManagementSystem.enums.PurchaseOrderStatus;
import com.emranhss.GarmentsManagementSystem.repository.GoodsReceiveNoteRepository;
import com.emranhss.GarmentsManagementSystem.repository.PurchaseOrderRepository;
import com.emranhss.GarmentsManagementSystem.repository.StockRepository;
import com.emranhss.GarmentsManagementSystem.service.GoodsReceiveNoteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsReceiveNoteServiceImpl  implements GoodsReceiveNoteService {


    private final GoodsReceiveNoteRepository goodsReceiveNoteRepository;

    private final PurchaseOrderRepository purchaseOrderRepository;

    private final StockRepository stockRepository;

    @Override
    public GoodsReceiveNoteResponseDto create(GoodsReceiveNoteRequestDto request) {
        PurchaseOrder purchaseOrder =
                purchaseOrderRepository.findById(
                                request.getPurchaseOrderId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Order not found."));

        if (goodsReceiveNoteRepository.existsByPurchaseOrderId(
                purchaseOrder.getId())) {

            throw new RuntimeException(
                    "This Purchase Order has already been received.");

        }

        GoodsReceiveNote grn =
                new GoodsReceiveNote();

        grn.setGrnNo(generateGrnNo());

        grn.setGrnDate(request.getGrnDate());

        grn.setPurchaseOrder(purchaseOrder);

        grn.setChallanNo(request.getChallanNo());

        grn.setRemarks(request.getRemarks());

        grn.setItems(
                buildGrnItems(grn, purchaseOrder));

        grn.setGrandTotal(
                purchaseOrder.getGrandTotal());

        updateStock(purchaseOrder);

        goodsReceiveNoteRepository.save(grn);

        purchaseOrder.setStatus(
                PurchaseOrderStatus.RECEIVED);

        purchaseOrderRepository.save(purchaseOrder);

        return GoodsReceiveNoteMapper.toDto(grn);

    }

    // =====================================
    // Generate GRN Number
    // =====================================

    private String generateGrnNo() {

        return goodsReceiveNoteRepository
                .findTopByOrderByIdDesc()
                .map(last -> {

                    long next =
                            last.getId() + 1;

                    return String.format(
                            "GRN-%05d",
                            next);

                })
                .orElse("GRN-00001");

    }


    // =====================================
    // Build GRN Items
    // =====================================

    private List<GoodsReceiveNoteItem> buildGrnItems(
            GoodsReceiveNote grn,
            PurchaseOrder purchaseOrder) {

        List<GoodsReceiveNoteItem> items = new ArrayList<>();

        for (PurchaseOrderItem poItem : purchaseOrder.getItems()) {

            GoodsReceiveNoteItem item =
                    new GoodsReceiveNoteItem();

            item.setGoodsReceiveNote(grn);

            item.setPurchaseOrderItem(poItem);

            item.setQuantity(poItem.getQuantity());

            item.setUnitPrice(poItem.getUnitPrice());

            item.setLineTotal(poItem.getLineTotal());

            items.add(item);

        }




        return items;

    }

    // =====================================
    // Update Stock
    // =====================================

    private void updateStock(
            PurchaseOrder purchaseOrder) {

        for (PurchaseOrderItem poItem
                : purchaseOrder.getItems()) {

            Stock stock =
                    stockRepository
                            .findByItemId(
                                    poItem.getItem().getId())
                            .orElseGet(() -> {

                                Stock s =
                                        new Stock();

                                s.setItem(
                                        poItem.getItem());

                                s.setAvailableQuantity(
                                        0.0);

                                return s;

                            });

            stock.setAvailableQuantity(

                    stock.getAvailableQuantity()

                            + poItem.getQuantity()

            );

            stockRepository.save(stock);

        }
    }



        @Override
    public GoodsReceiveNoteResponseDto getById(Long id) {
            GoodsReceiveNote grn =
                    goodsReceiveNoteRepository.findById(id)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "GRN not found."));

            return GoodsReceiveNoteMapper.toDto(grn);
    }

    @Override
    public List<GoodsReceiveNoteResponseDto> getAll() {
        return goodsReceiveNoteRepository.findAll()
                .stream()
                .map(GoodsReceiveNoteMapper::toDto)
                .toList();
    }
}
