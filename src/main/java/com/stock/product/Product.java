package com.stock.product;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product extends PanacheEntityBase {

    @Id
    @NotNull
    @Column(nullable = false, unique = true, length = 20)
    public String code;

    @NotNull
    @Column(nullable = false)
    public String name;

    @NotNull
    @Column(nullable = false)
    @Positive
    public BigDecimal price;
}
