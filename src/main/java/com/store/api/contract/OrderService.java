package com.store.api.contract;

import com.store.api.model.Order;

import java.util.List;

public interface OrderService {

    public Order createOrder(Order order);
    public List<Order> listOrders();

}
