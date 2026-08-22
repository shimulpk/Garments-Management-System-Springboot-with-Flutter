package com.emranhss.GarmentsManagementSystem.serviceimp;


import com.emranhss.GarmentsManagementSystem.dto.mapper.RmcMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.RmcCheckRequestDTO;
import com.emranhss.GarmentsManagementSystem.dto.response.RmcCheckResponseDTO;
import com.emranhss.GarmentsManagementSystem.entity.*;
import com.emranhss.GarmentsManagementSystem.repository.BomStyleRepository;
import com.emranhss.GarmentsManagementSystem.repository.BomViewRepository;
import com.emranhss.GarmentsManagementSystem.repository.OrderRepository;
import com.emranhss.GarmentsManagementSystem.repository.RmcCheckRepository;
import com.emranhss.GarmentsManagementSystem.service.RmcCheckService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RmcCheckServiceImpl implements RmcCheckService {

    private final OrderRepository orderRepository;

    private final BomViewRepository bomViewRepository;

    private final RmcCheckRepository rmcCheckRepository;


    @Override
    public RmcCheckResponseDTO generate(RmcCheckRequestDTO request) {
        Order order =
                orderRepository.findById(
                        request.getOrderId()
                ).orElseThrow(() ->
                        new RuntimeException("Order Not Found"));

        BomStyle style =
                order.getStyle();

       


        List<BomView> bomViews =
                bomViewRepository.findByStyleId(
                        style.getId()
                );

        if (bomViews.isEmpty()) {

            throw new RuntimeException(
                    "No BOM Found For This Style");

        }

        RmcCheck rmcCheck = new RmcCheck();

        rmcCheck.setOrder(order);



        rmcCheck.setStyle(style);

        rmcCheck.setBuyerName(
                order.getBuyer().getBuyerName());

        rmcCheck.setStyleName(
                style.getStyleName());

        rmcCheck.setTotalOrderQty(
                order.getTotalQuantity());

        double grandTotalCost = 0;

        for (BomView bom : bomViews) {

            RmcDetails details =
                    new RmcDetails();

            details.setRmcCheck(rmcCheck);

            details.setMaterialName(
                    bom.getMaterialName());

            details.setUnit(
                    bom.getUnit());

            double qtyPerPiece =
                    bom.getQuantity().doubleValue();

            double unitPrice =
                    bom.getUnitPrice().doubleValue();

            double requiredQty =
                    order.getTotalQuantity() * qtyPerPiece;

            double materialCost =
                    requiredQty * unitPrice;

            details.setQtyPerPiece(
                    qtyPerPiece);

            details.setUnitPrice(
                    unitPrice);

            details.setTotalQtyRequired(
                    requiredQty);

            details.setTotalMaterialCost(
                    materialCost);

            grandTotalCost += materialCost;

            rmcCheck.getRmcDetailsList()
                    .add(details);

        }

        rmcCheck.setGrandTotalCost(
                grandTotalCost);

        RmcCheck saved =
                rmcCheckRepository.save(rmcCheck);

        return RmcMapper.toDto(saved);
    }

    @Override
    public RmcCheckResponseDTO getById(Long id) {
        RmcCheck rmcCheck =
                rmcCheckRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("RMC Check Not Found"));

        return RmcMapper.toDto(rmcCheck);

    }

    @Override
    public List<RmcCheckResponseDTO> getAll() {
        return rmcCheckRepository.findAll()
                .stream()
                .map(RmcMapper::toDto)
                .toList();

    }

    @Override
    public void delete(Long id) {
        RmcCheck rmcCheck =
                rmcCheckRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("RMC Check Not Found"));

        rmcCheckRepository.delete(rmcCheck);
    }
}

