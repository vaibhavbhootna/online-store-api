package com.store.api.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "ORDER_ITEMS")
@Data
@Table(name = "ORDER_ITEMS")
public class OrderItemDO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long orderItemId;
    @ManyToOne(targetEntity=OrderDO.class)
    private OrderDO orderDO;
    @OneToOne(targetEntity=ProductDO.class)
    private ProductDO productDO;
    private Long unit;
    private BigDecimal unitPrice;
}
