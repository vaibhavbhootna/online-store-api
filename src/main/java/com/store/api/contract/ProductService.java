package com.store.api.contract;

import com.store.api.model.Product;

import java.util.List;

public interface ProductService {

    public Product addProduct (Product product);
    public List<Product> listProducts ();
}
