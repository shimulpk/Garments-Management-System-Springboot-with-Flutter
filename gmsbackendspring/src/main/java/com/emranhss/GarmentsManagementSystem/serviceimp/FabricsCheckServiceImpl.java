package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.FabricsCheckMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.FabricsCheckRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.FabricsCheckResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.*;
import com.emranhss.GarmentsManagementSystem.repository.OrderRepository;
import com.emranhss.GarmentsManagementSystem.repository.FabricsCheckRepository;
import com.emranhss.GarmentsManagementSystem.repository.UomRepository;
import com.emranhss.GarmentsManagementSystem.service.FabricsCheckService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FabricsCheckServiceImpl implements FabricsCheckService {

    private final FabricsCheckRepository fabricsCheckRepository;
    private final OrderRepository orderRepository;
    private final UomRepository uomRepository;


    @Override
    public FabricsCheckResponseDto generate(FabricsCheckRequestDto request) {
        Order order =
                orderRepository.findById(
                                request.getOrderId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order Not Found"));

        FabricsCheck fc =
                new FabricsCheck();

        fc.setOrder(order);
        fc.setStyle(order.getStyle());

        fc.setOrderCode(
                order.getOrderId());

        fc.setPoNumber(
                order.getPoNumber());

        fc.setCreatedAt(
                LocalDateTime.now());

        BigDecimal totalFabric =
                BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {

            Optional<Uom> uomOptional =

                    uomRepository
                            .findByProductNameAndSize(
                                    item.getType(),
                                    item.getSize());

            FabricDetails detail =
                    new FabricDetails();

            detail.setProductName(
                    item.getType());

            detail.setSize(
                    item.getSize());

            detail.setType(
                    item.getType());

            detail.setQty(
                    item.getQuantity());

            detail.setFabricsCheck(fc);

            if (uomOptional.isPresent()) {

                Uom uom =
                        uomOptional.get();

                BigDecimal baseFabric =
                        uom.getTotalBaseFabric();

                BigDecimal requiredFabric =

                        baseFabric.multiply(
                                BigDecimal.valueOf(
                                        item.getQuantity()));

                detail.setBaseFabric(
                        baseFabric);

                detail.setCalculatedFabric(
                        requiredFabric);

                detail.setHasUom(true);

                totalFabric =
                        totalFabric.add(
                                requiredFabric);

            } else {

                detail.setBaseFabric(
                        BigDecimal.ZERO);

                detail.setCalculatedFabric(
                        BigDecimal.ZERO);

                detail.setHasUom(false);
            }

            fc.getDetails().add(detail);
        }

        fc.setTotalFabricRequired(
                totalFabric);

        FabricsCheck saved =

                fabricsCheckRepository
                        .save(fc);

        return FabricsCheckMapper
                .toDto(saved);
    }

    @Override
    public FabricsCheckResponseDto getById(Long id) {
        FabricsCheck fc =
                fabricsCheckRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "RMC Not Found"));

        return FabricsCheckMapper
                .toDto(fc);
    }

    @Override
    public List<FabricsCheckResponseDto> getAll() {
        return fabricsCheckRepository
                .findAll()
                .stream()
                .map(FabricsCheckMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        FabricsCheck fc =
                fabricsCheckRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Fc Not Found"));

        fabricsCheckRepository
                .delete(fc);
    }

    @Override
    public FabricsCheckResponseDto getByOrder(Long orderId) {
        FabricsCheck fabricsCheck =
                fabricsCheckRepository
                        .findByOrder_Id(orderId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Fabric Record Not Found"));

        return FabricsCheckMapper
                .toDto(fabricsCheck);
    }
}
