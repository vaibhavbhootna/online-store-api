package com.store.api.controller;

import com.store.api.contract.ProductService;
import com.store.api.model.Product;
import com.store.api.persistence.ProductRepository;
import com.store.api.persistence.model.ProductDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Product addProduct(@RequestBody Product product){
        ProductDO productDO = getProductDO(product);
        ProductDO newProductDO = repository.save(productDO);
        return getProduct(newProductDO);
    }

    @Override
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> listProducts() {
        List<ProductDO> productDOList = repository.findAll();
        return productDOList.stream().map(productDO
                -> getProduct(productDO)).collect(Collectors.toList());
    }

    private ProductDO getProductDO(Product product) {
        ProductDO productDO = new ProductDO();
        productDO.setId(product.getId());
        productDO.setCode(product.getCode());
        productDO.setPrice(product.getPrice());
        productDO.setName(product.getName());
        productDO.setDescription(product.getDescription());
        productDO.setCategories(product.getCategories());
        return productDO;
    }

    private Product getProduct(ProductDO productDO) {
        Product product = new Product();
        product.setId(productDO.getId());
        product.setName(productDO.getName());
        product.setCode(productDO.getCode());
        product.setPrice(productDO.getPrice());
        product.setDescription(productDO.getDescription());
        product.setCategories(productDO.getCategories());
        return product;
    }


}