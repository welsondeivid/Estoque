package com.stock.raw;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RawMaterialRepository implements PanacheRepository<RawMaterial> {

    public RawMaterial findByCode(String code){
        return find("code", code).firstResult();
    }

    public boolean existsByCode (String code){
        return count("code", code) > 0;
    }

    public RawMaterial update(RawMaterial rawMaterial, RawMaterial newMaterial){
        rawMaterial.name = newMaterial.name;
        rawMaterial.amount = newMaterial.amount;

        return rawMaterial;
    }
}
