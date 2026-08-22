package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.request.OrderRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.OrderItemResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.OrderResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Order;
import com.emranhss.GarmentsManagementSystem.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public class OrderMapper {
    public static Order toEntity(OrderRequestDto dto) {

        if (dto == null) {
            return null;
        }

        Order order = new Order();

        order.setOrderId(dto.getOrderId());
        order.setPoNumber(dto.getPoNumber());
        order.setOrderDate(dto.getOrderDate());
        order.setShipDate(dto.getShipDate());
        order.setStatus(dto.getStatus());
        order.setShippingAddress(dto.getShippingAddress());

        return order;
    }

    public static OrderResponseDto toDto(Order order) {

        if (order == null) {
            return null;
        }

        OrderResponseDto dto =
                new OrderResponseDto();

        dto.setId(order.getId());

        dto.setOrderId(order.getOrderId());
        dto.setPoNumber(order.getPoNumber());



        dto.setOrderDate(order.getOrderDate());
        dto.setShipDate(order.getShipDate());

        dto.setStatus(order.getStatus());
        dto.setShippingAddress(order.getShippingAddress());

        dto.setTotalQuantity(order.getTotalQuantity());

        dto.setSubtotal(order.getSubtotal());
        dto.setVatPercent(order.getVatPercent());
        dto.setVat(order.getVat());
        dto.setGrandTotal(order.getGrandTotal());


        // Buyer

        if (order.getBuyer() != null) {

            dto.setBuyerId(
                    order.getBuyer().getId());

            dto.setBuyerCode(
                    order.getBuyer().getBuyerCode());

            dto.setBuyerName(
                    order.getBuyer().getBuyerName());
        }

        // Style

        if (order.getStyle() != null) {

            dto.setStyleId(
                    order.getStyle().getId());

            dto.setStyleCode(
                    order.getStyle().getStyleCode());

            dto.setStyleName(
                    order.getStyle().getStyleName());
        }

        // Order Items

        if (order.getItems() != null) {

            List<OrderItemResponseDto> itemDtos =

                    order.getItems()
                            .stream()
                            .map(OrderMapper::mapItem)
                            .toList();

            dto.setItems(itemDtos);
        }

        return dto;
    }

    private static OrderItemResponseDto mapItem(
            OrderItem item) {

        OrderItemResponseDto dto =
                new OrderItemResponseDto();

        dto.setId(item.getId());
        dto.setType(item.getType());
        dto.setSize(item.getSize());
        dto.setColor(item.getColor());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());

        Integer qty =
                item.getQuantity() == null
                        ? 0
                        : item.getQuantity();

        BigDecimal unitPrice =
                item.getUnitPrice() == null
                        ? BigDecimal.ZERO
                        : item.getUnitPrice();

        dto.setLineTotal(
                unitPrice.multiply(
                        BigDecimal.valueOf(qty)
                )
        );

        return dto;
    }
}
