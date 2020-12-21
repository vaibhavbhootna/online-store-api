package com.store.api.impl;

import com.store.api.contract.OrderService;
import com.store.api.model.Order;
import com.store.api.model.OrderItem;
import com.store.api.persistence.OrderRepository;
import com.store.api.persistence.ProductRepository;
import com.store.api.persistence.model.OrderDO;
import com.store.api.persistence.model.OrderItemDO;
import com.store.api.persistence.model.ProductDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        OrderDO orderDO = new OrderDO();
        Set<OrderItemDO> orderItemDOList = new HashSet<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        if(order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                Optional<ProductDO> productDO = productRepository.findById(item.getProductId());
                if (productDO.isPresent()) {
                    OrderItemDO itemDO = new OrderItemDO();
                    itemDO.setProductDO(productDO.get());
                    itemDO.setUnitPrice(productDO.get().getPrice());
                    itemDO.setUnit(item.getUnit());
                    totalAmount = totalAmount.add(productDO.get().getPrice().multiply(BigDecimal.valueOf(item.getUnit())));
                    orderItemDOList.add(itemDO);
                } else {
                    throw new RuntimeException("Product not available");
                }
            }
            orderDO.setUserId(order.getUserId());
            orderDO.setTotalAmount(totalAmount);
            orderDO.setOrderItems(orderItemDOList);
            orderDO.setStatus("SUBMITTED");
            OrderDO createdOrder = orderRepository.save(orderDO);
            return getOrder(createdOrder);
        }
        return null;
    }

    @Override
    public List<Order> listOrders() {
        List<OrderDO> orderDOList = orderRepository.findAll();
        return orderDOList
                .stream()
                .map(orderDO -> getOrder(orderDO))
                .collect(Collectors.toList());
    }

    private Order getOrder(OrderDO orderDO) {
        Order order = new Order();
        order.setOrderId(orderDO.getOrderId());
        order.setUserId(orderDO.getUserId());
        order.setUserId(orderDO.getUserId());
        order.setStatus(orderDO.getStatus());
        order.setTotalAmount(orderDO.getTotalAmount());
        order.setOrderItems(orderDO.getOrderItems()
                .stream()
                .map(this::getOrderItem)
                .collect(Collectors.toSet()));
        return order;
    }

    private OrderItem getOrderItem(OrderItemDO orderItemDO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setUnit(orderItemDO.getUnit());
        orderItem.setProductId(orderItemDO.getProductDO().getProductId());
        orderItem.setUnitPrice(orderItemDO.getUnitPrice());
        return orderItem;
    }
}
