package com.stock.raw;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "raw_material")
public class RawMaterial extends PanacheEntityBase{

    @Id
    @NotNull
    @Column(nullable = false, unique = true, length = 20)
    public String code;

    @NotNull
    @Column(nullable = false)
    public String name;

    @NotNull
    @Column(nullable = false)
    public int amount;
}
