package com.store.api.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class Order {

    private Long orderId;
    private String userId;
    private Set<OrderItem> orderItems;
    private BigDecimal totalAmount;
    private String status;

}
