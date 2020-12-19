package com.store.api.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "PRODUCT", uniqueConstraints={@UniqueConstraint(columnNames={"code"})})
public class ProductDO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true, nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    @ElementCollection
    private List<String> categories;
    @Column(nullable = false)
    private BigDecimal price;

}
