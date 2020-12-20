package com.store.api.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "ORDER_ITEMS")
@Data
@Table(name = "ORDER_ITEMS")
public class OrderItemDO {

    @Id
    private Long orderItemId;
    private Long orderId;
    @OneToOne(targetEntity=OrderItemDO.class)
    private ProductDO productDO;
    private Long unit;
    private BigDecimal unitPrice;
}
