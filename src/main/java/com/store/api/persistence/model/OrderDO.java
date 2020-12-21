package com.store.api.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "ORDERS")
@Data
@Table(name = "ORDERS")
public class OrderDO implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long orderId;
    private String userId;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<OrderItemDO> orderItems = new HashSet<>();
    private BigDecimal totalAmount;
    private String status;
}
