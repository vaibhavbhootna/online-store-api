package com.store.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.api.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(classes = com.store.api.controller.ProductController.class, properties = "spring.profiles.active:test")
@AutoConfigureMockMvc
@EnableWebMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private static WebApplicationContext webContext;
    private static StaticApplicationContext context;

    @Autowired
    private ProductController productController;

    @BeforeAll
    public static void setupAll() {
        context = new StaticApplicationContext(webContext);
    }

    @Test
    public void testAddProduct_PostMapping() throws Exception {
        Product product = createSampleProduct();
        String requestJson = new ObjectMapper().writeValueAsString(product);
        MockHttpServletRequestBuilder request = prepareRequest("/product/add", requestJson);
        MvcResult result = this.mvc.perform(request).andReturn();
        String actualResponse = result.getResponse().getContentAsString();
        assertEquals(requestJson, actualResponse);
    }

    @Test
    public void testAddProduct(){
        Product product = createSampleProduct();
        Product actualResponse = productController.addProduct(product);
        assertEquals(product, actualResponse);
    }

    private MockHttpServletRequestBuilder prepareRequest(String url, String requestJson) {
        MockHttpServletRequestBuilder request  = post(url);
        request.content(requestJson);
        request.characterEncoding("UTF-8");
        request.contentType("application/json;charset=UTF-8");
        request.accept("application/json;charset=UTF-8");
        return request;
    }

    private Product createSampleProduct() {
        Product product = new Product();
        product.setName("Apple Watch");
        product.setDescription("Apple Watch is a wearable smartwatch that allows users to accomplish a variety of tasks,");
        product.setCategories(Arrays.asList("Watch", "Gadget", "Wearable"));
        return product;
    }
}
