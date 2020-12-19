package com.store.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private List<String> categories;

}
