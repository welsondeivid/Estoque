package com.stock.production;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class ProductionRepository {

    @Inject
    EntityManager em;

    public List<StockDTO> availableProducts(){
        return em.createQuery("""
            SELECT new com.stock.production.StockDTO(
                p.code,
                p.name,
                p.price,
                rm.amount,
                c.required
            )
            FROM Product p
            JOIN Composition c ON c.id.productCode = p.code
            JOIN RawMaterial rm ON rm.code = c.id.rawMaterialCode
        """, StockDTO.class).getResultList();
    }
}
