package com.stock.compostion;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CompositionRepository
        implements PanacheRepository<Composition> {

    public Composition findByCodes(String productCode, String rawMaterialCode) {
        return find(
                "id.productCode = ?1 and id.rawMaterialCode = ?2",
                productCode,
                rawMaterialCode
        ).firstResult();
    }

    public List<Composition> findByProduct(String productCode) {
        return list("id.productCode", productCode);
    }

    public void deleteByProduct(String productCode) {
        delete("id.productCode", productCode);
    }
}