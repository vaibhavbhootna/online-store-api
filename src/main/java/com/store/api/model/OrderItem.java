package com.store.api.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {

    private Long productId;
    private Long unit;
    private BigDecimal unitPrice;
}
