package com.store.api.controller;

import com.store.api.contract.ProductService;
import com.store.api.model.Product;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController implements ProductService {

    @Override
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Product addProduct(@RequestBody Product product){
        return product;
    }

    @Override
    public List<Product> listProducts() {
        return null;
    }
}