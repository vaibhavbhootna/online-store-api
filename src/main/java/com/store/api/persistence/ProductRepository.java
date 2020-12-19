package com.store.api.persistence;

import com.store.api.persistence.model.ProductDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductDO, Long> {

    public ProductDO save(ProductDO productDO);
    public List<ProductDO> findAll();
}
