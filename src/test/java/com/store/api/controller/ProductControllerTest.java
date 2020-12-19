package com.store.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.api.main.OnlineStoreApplication;
import com.store.api.model.Product;
import com.store.api.persistence.ProductRepository;
import com.store.api.persistence.model.ProductDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(classes = OnlineStoreApplication.class, properties = "spring.profiles.active:test")
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductRepository repository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(ProductControllerTest.class);
        mvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    private static HttpHeaders httpHeaders = new HttpHeaders();

    static {
        httpHeaders.set("Content-Type","application/json;charset=UTF-8");
        httpHeaders.set("Accept","application/json;charset=UTF-8");
    }

    @Test
    public void testWrongMappings() throws Exception {
        Product product = createSampleProduct();
        String requestJson = new ObjectMapper().writeValueAsString(product);
        MockHttpServletRequestBuilder request = post("/product/addProduct");
        MvcResult result =this.mvc.perform(request
                .headers(httpHeaders)
                .content(requestJson))
                .andReturn();

        assertEquals(result.getResponse().getStatus(), 404);
    }

    @Test
    public void testAddProduct_Success() throws Exception {
        Mockito.when(repository.save(Mockito.any())).thenReturn(createSampleProductDO());
        Product product = createSampleProduct();
        String requestJson = new ObjectMapper().writeValueAsString(product);
        MockHttpServletRequestBuilder request = post("/product/add");
        MvcResult result =this.mvc.perform(request
                .headers(httpHeaders)
                .content(requestJson))
                .andReturn();

        String actualResponse = result.getResponse().getContentAsString();
        product.setId(1l);
        String expected = new ObjectMapper().writeValueAsString(product);
        assertEquals(expected, actualResponse);
        assertEquals(result.getResponse().getStatus(), 200);
    }

    @Test
    public void testAddProduct_NotSupportedMethods() throws Exception {
        List<MockHttpServletRequestBuilder> requests = Arrays.asList(
        get("/product/add"),
                put("/product/add"),
                delete("/product/add"));

        for(MockHttpServletRequestBuilder request : requests) {
            MvcResult result = this.mvc.perform(request
                    .headers(httpHeaders))
                    .andReturn();
            assertEquals(405, result.getResponse().getStatus());
        }
    }

    @Test
    public void testListProduct_Success() throws Exception {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(createSampleProductDO()));
        MockHttpServletRequestBuilder request = get("/product/list");
        MvcResult result =this.mvc.perform(request
                .headers(httpHeaders))
                .andReturn();

        String actualResponse = result.getResponse().getContentAsString();
        Product product = createSampleProduct();
        product.setId(1l);
        String expected = new ObjectMapper().writeValueAsString(Arrays.asList(product));
        assertEquals(expected, actualResponse);
        assertEquals(result.getResponse().getStatus(), 200);
    }

    @Test
    public void testListProduct_NotSupportedMethods() throws Exception {
        List<MockHttpServletRequestBuilder> requests = Arrays.asList(
                post("/product/list"),
                put("/product/list"),
                delete("/product/list"));

        for(MockHttpServletRequestBuilder request : requests) {
            MvcResult result = this.mvc.perform(request
                    .headers(httpHeaders))
                    .andReturn();
            assertEquals(405, result.getResponse().getStatus());
        }
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
        product.setId(1l);
        product.setName("Apple Watch");
        product.setCode("AppleWatch-6-2020");
        product.setDescription("Apple Watch is a wearable smartwatch that allows users to accomplish a variety of tasks,");
        product.setPrice(BigDecimal.valueOf(551));
        product.setCategories(Arrays.asList("Watch", "Gadget", "Wearable"));
        return product;
    }

}
