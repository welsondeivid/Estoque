package com.stock.compostion;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CompositionId implements Serializable {

    @NotNull
    @Column(nullable = false, name = "product_code", length = 20)
    public String productCode;

    @NotNull
    @Column(nullable = false, name = "raw_material_code", length = 20)
    public String rawMaterialCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompositionId)) return false;
        CompositionId that = (CompositionId) o;
        return Objects.equals(productCode, that.productCode) &&
                Objects.equals(rawMaterialCode, that.rawMaterialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCode, rawMaterialCode);
    }
}
