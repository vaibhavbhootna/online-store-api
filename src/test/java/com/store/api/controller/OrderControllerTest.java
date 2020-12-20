package com.store.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.api.main.OnlineStoreApplication;
import com.store.api.model.Order;
import com.store.api.model.OrderItem;
import com.store.api.model.Product;
import com.store.api.persistence.OrderRepository;
import com.store.api.persistence.ProductRepository;
import com.store.api.persistence.model.ProductDO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(classes = OnlineStoreApplication.class, properties = "spring.profiles.active:test")
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductController productController;
    @Autowired
    private OrderController orderController;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Mocking JPA repository using Mockito
     */
    @BeforeEach
    public void setUp() {
     //   MockitoAnnotations.initMocks(ProductControllerTest.class);
       // mvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    private static HttpHeaders httpHeaders = new HttpHeaders();

    static {
        httpHeaders.set("Content-Type","application/json;charset=UTF-8");
        httpHeaders.set("Accept","application/json;charset=UTF-8");
    }

    @Test
    @DisplayName("Testing URL mapping '/product/add'. API must save the product in DB.")
    public void testCreateOrder_Success() throws Exception {
        productRepository.save(createSampleProductDO());
        Order order = new Order();

        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(1l);
        orderItem.setUnit(2l);
        Set<OrderItem> orderItems = Collections.singleton(orderItem);
        order.setOrderItems(orderItems);

        String requestJson = new ObjectMapper().writeValueAsString(order);
        MockHttpServletRequestBuilder request = post("/order/create");
        MvcResult result =this.mvc.perform(request
                .headers(httpHeaders)
                .content(requestJson))
                .andReturn();

        Assertions.assertNotNull(result.getResponse().getContentAsString());
    }


    private Product createSampleProduct() {
        Product product = new Product();
        product.setName("Apple Watch");
        product.setCode("AppleWatch-6-2020");
        product.setDescription("Apple Watch is a wearable smartwatch that allows users to accomplish a variety of tasks,");
        product.setPrice(BigDecimal.valueOf(551));
        product.setCategories(Arrays.asList("Watch", "Gadget", "Wearable"));
        return product;
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
