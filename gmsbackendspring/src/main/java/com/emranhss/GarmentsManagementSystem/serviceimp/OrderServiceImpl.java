package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.OrderMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.OrderRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.OrderResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.BomStyle;
import com.emranhss.GarmentsManagementSystem.entity.Buyer;
import com.emranhss.GarmentsManagementSystem.entity.Order;
import com.emranhss.GarmentsManagementSystem.entity.OrderItem;
import com.emranhss.GarmentsManagementSystem.enums.ProductionStage;
import com.emranhss.GarmentsManagementSystem.repository.*;
import com.emranhss.GarmentsManagementSystem.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final BuyerRepository buyerRepository;
    private final BomStyleRepository bomStyleRepository;
    private final RmcCheckRepository rmcCheckRepository;
    private final FabricsCheckRepository fabricCheckRepository;


    @Override
    public OrderResponseDto create(OrderRequestDto request) {
        Buyer buyer =
                buyerRepository.findById(request.getBuyerId())
                        .orElseThrow(() ->
                                new RuntimeException("Buyer Not Found"));

        BomStyle style =
                bomStyleRepository.findById(request.getStyleId())
                        .orElseThrow(() ->
                                new RuntimeException("Style Not Found"));

        Order order =
                OrderMapper.toEntity(request);

        order.setBuyer(buyer);
        order.setStyle(style);


        buildOrderItems(order, request);

        calculateTotals(order, request);

        Order savedOrder =
                orderRepository.save(order);

        return OrderMapper.toDto(savedOrder);
    }

    @Override
    public OrderResponseDto update(Long id, OrderRequestDto request) {
        Order order =
                orderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Order Not Found"));

        Buyer buyer =
                buyerRepository.findById(request.getBuyerId())
                        .orElseThrow(() ->
                                new RuntimeException("Buyer Not Found"));

        BomStyle style =
                bomStyleRepository.findById(request.getStyleId())
                        .orElseThrow(() ->
                                new RuntimeException("Style Not Found"));

        order.setOrderId(request.getOrderId());
        order.setPoNumber(request.getPoNumber());
        order.setOrderDate(request.getOrderDate());
        order.setShipDate(request.getShipDate());
        order.setStatus(request.getStatus());
        order.setShippingAddress(request.getShippingAddress());

        order.setBuyer(buyer);
        order.setStyle(style);

        order.getItems().clear();

        buildOrderItems(order, request);

        calculateTotals(order, request);

        Order updatedOrder =
                orderRepository.save(order);

        return OrderMapper.toDto(updatedOrder);
    }

    @Override
    public OrderResponseDto getById(Long id) {
        Order order =
                orderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Order Not Found"));

        return OrderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDto> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Order order =
                orderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Order Not Found"));

        orderRepository.delete(order);
    }

    @Override
    public List<OrderResponseDto> getByBuyer(Long buyerId) {
        return orderRepository.findByBuyerId(buyerId)
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderResponseDto> getAvailableOrdersByBuyer(Long buyerId) {
        return orderRepository
                .findAvailableOrdersByBuyer(buyerId)
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderResponseDto> getAvailableForRawMaterialCheck() {
        return orderRepository.findAll()
                .stream()
                .filter(order ->
                        !rmcCheckRepository.existsByOrderId(order.getId()))
                .map(OrderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderResponseDto> getAvailableForFabricCheck() {
        return orderRepository.findAll()
                .stream()
                .filter(order ->
                        !fabricCheckRepository.existsByOrderId(order.getId()))
                .map(OrderMapper::toDto)
                .toList();
    }

    private void buildOrderItems(Order order,
                                 OrderRequestDto request) {

        if (request.getItems() == null) {
            return;
        }

        List<OrderItem> items =
                request.getItems()
                        .stream()
                        .map(dto -> {

                            OrderItem item =
                                    new OrderItem();

                            item.setType(dto.getType());
                            item.setSize(dto.getSize());
                            item.setColor(dto.getColor());
                            item.setQuantity(dto.getQuantity());
                            item.setUnitPrice(dto.getUnitPrice());

                            item.setOrder(order);

                            return item;

                        }).toList();

        order.getItems().addAll(items);
    }

    private void calculateTotals(Order order, OrderRequestDto request) {

        int totalQty = 0;

        BigDecimal subtotal = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {

            Integer qty =
                    item.getQuantity() == null
                            ? 0
                            : item.getQuantity();

            BigDecimal unitPrice =
                    item.getUnitPrice() == null
                            ? BigDecimal.ZERO
                            : item.getUnitPrice();

            totalQty += qty;

            subtotal =
                    subtotal.add(
                            unitPrice.multiply(
                                    BigDecimal.valueOf(qty)
                            )
                    );
        }

        BigDecimal vatPercent =
                request.getVatPercent() == null
                        ? BigDecimal.ZERO
                        : request.getVatPercent();

        BigDecimal vat =
                subtotal
                        .multiply(vatPercent)
                        .divide(BigDecimal.valueOf(100));

        BigDecimal grandTotal =
                subtotal.add(vat);

        order.setTotalQuantity(totalQty);
        order.setSubtotal(subtotal);
        order.setVatPercent(vatPercent);
        order.setVat(vat);
        order.setGrandTotal(grandTotal);

    }
}
