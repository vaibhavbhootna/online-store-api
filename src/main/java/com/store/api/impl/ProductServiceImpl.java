package com.store.api.impl;

import com.store.api.contract.ProductService;
import com.store.api.model.Product;
import com.store.api.persistence.ProductRepository;
import com.store.api.persistence.model.ProductDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;


    @Override
    public Product addProduct(Product product) {
        ProductDO productDO = getProductDO(product);
        try {
            ProductDO newProductDO = repository.save(productDO);
            return getProduct(newProductDO);
        } catch (Exception exception){
            throw new RuntimeException ("Error adding product");
        }
    }

    @Override
    public List<Product> listProducts() {
        List<ProductDO> productDOList = repository.findAll();
        return productDOList.stream().map(productDO
                -> getProduct(productDO)).collect(Collectors.toList());
    }

    private ProductDO getProductDO(Product product) {
        ProductDO productDO = new ProductDO();
        productDO.setProductId(product.getId());
        productDO.setProductCode(product.getCode());
        productDO.setPrice(product.getPrice());
        productDO.setProductName(product.getName());
        productDO.setDescription(product.getDescription());
        productDO.setCategories(product.getCategories());
        return productDO;
    }

    private Product getProduct(ProductDO productDO) {
        Product product = new Product();
        product.setId(productDO.getProductId());
        product.setName(productDO.getProductName());
        product.setCode(productDO.getProductCode());
        product.setPrice(productDO.getPrice());
        product.setDescription(productDO.getDescription());
        product.setCategories(productDO.getCategories());
        return product;
    }
}
