package com.store.api.persistence;

import com.store.api.main.OnlineStoreApplication;
import com.store.api.persistence.model.ProductDO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test ProductRepository to ensure data fetch and save
 */
@SpringBootTest(classes = OnlineStoreApplication.class, properties = "spring.profiles.active:test")
@AutoConfigureMockMvc
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    @DisplayName("Test save method of JPA Repository to save product in DB")
    public void testSave(){
        ProductDO productDO = createSampleProductDO();
        productDO.setCode("AppleWatch-SE-2020");
        assertEquals(productDO, repository.save(productDO));
    }

    @Test
    @DisplayName("Test findAll method of JPA Repository to fetch saved products in DB")
    public void testFindAll(){
        ProductDO productDO = createSampleProductDO();
        repository.save(productDO);
        List<ProductDO> productDOList = repository.findAll();
        assertTrue(productDOList.size()>0);
    }

    private ProductDO createSampleProductDO() {
        ProductDO product = new ProductDO();
        product.setName("Apple Watch");
        product.setCode("AppleWatch-6-2020");
        product.setDescription("Apple Watch is a wearable smartwatch that allows users to accomplish a variety of tasks,");
        product.setPrice(BigDecimal.valueOf(551));
        product.setCategories(Arrays.asList("Watch", "Gadget", "Wearable"));
        return product;
    }
}
