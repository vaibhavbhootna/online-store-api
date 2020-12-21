package com.store.api.impl;

import com.store.api.main.OnlineStoreApplication;
import com.store.api.model.Order;
import com.store.api.model.OrderItem;
import com.store.api.persistence.OrderRepository;
import com.store.api.persistence.ProductRepository;
import com.store.api.persistence.model.ProductDO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = OnlineStoreApplication.class, properties = "spring.profiles.active:test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testCreateOrder_Success() throws Exception {
        productRepository.save(createSampleProductDO());
        orderRepository.deleteAll();
        Order order = createSampleOrder();
        order.setUserId("TEST");
        Order actualOrder = orderService.createOrder(order);

        Order expectedOrder = new Order();
        OrderItem expectedOrderItem = new OrderItem();
        expectedOrder.setUserId("TEST");
        expectedOrder.setOrderId(1l);
        expectedOrderItem.setProductId(1l);
        expectedOrderItem.setUnit(2l);
        expectedOrderItem.setUnitPrice(new BigDecimal("551.00"));
        Set<OrderItem> expectedOrderItems = Collections.singleton(expectedOrderItem);
        expectedOrder.setTotalAmount(new BigDecimal("1102.00"));
        expectedOrder.setOrderItems(expectedOrderItems);
        expectedOrder.setStatus("SUBMITTED");

        assertEquals(expectedOrder,actualOrder);
    }

    private Order createSampleOrder() {
        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(1l);
        orderItem.setUnit(2l);
        Set<OrderItem> orderItems = Collections.singleton(orderItem);
        order.setOrderItems(orderItems);
        return order;
    }

    @Test
    @DisplayName("Testing URL mapping '/product/add'. API must save the product in DB.")
    public void testListOrder_Success() throws Exception {
        productRepository.save(createSampleProductDO());
        Order order = createSampleOrder();
        order.setUserId("TEST");
        orderService.createOrder(order);
        List<Order> actualOrders = orderService.listOrders();


        Order expectedOrder = new Order();
        OrderItem expectedOrderItem = new OrderItem();
        expectedOrder.setUserId("TEST");
        expectedOrder.setOrderId(1l);
        expectedOrderItem.setProductId(1l);
        expectedOrderItem.setUnit(2l);
        expectedOrderItem.setUnitPrice(new BigDecimal("551.00"));
        Set<OrderItem> expectedOrderItems = Collections.singleton(expectedOrderItem);
        expectedOrder.setTotalAmount(new BigDecimal("1102.00"));
        expectedOrder.setOrderItems(expectedOrderItems);
        expectedOrder.setStatus("SUBMITTED");

        Assertions.assertTrue(actualOrders.contains(expectedOrder));
    }

    private ProductDO createSampleProductDO() {
        ProductDO product = new ProductDO();
        product.setProductId(1l);
        product.setProductName("Apple Watch");
        product.setProductCode("AppleWatch-6-2020");
        product.setDescription("Apple Watch is a wearable smartwatch that allows users to accomplish a variety of tasks,");
        product.setPrice(BigDecimal.valueOf(551));
        product.setCategories(Arrays.asList("Watch", "Gadget", "Wearable"));
        return product;
    }

}
