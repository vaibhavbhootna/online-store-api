package com.store.api.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity(name = "PRODUCTS")
@Table(name = "PRODUCTS", uniqueConstraints={@UniqueConstraint(columnNames={"productCode"})})
public class ProductDO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long productId;
    @Column(unique=true, nullable = false)
    private String productCode;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    @ElementCollection
    private List<String> categories;
    @Column(nullable = false)
    private BigDecimal price;

}
