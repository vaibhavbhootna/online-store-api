package com.store.api.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "ORDERS")
@Data
@Table(name = "ORDERS")
public class OrderDO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long orderId;
    private String userId;
    @OneToMany(mappedBy="orderId")
    private Set<OrderItemDO> orderItems;
    private BigDecimal totalAmount;
    private String status;
}
