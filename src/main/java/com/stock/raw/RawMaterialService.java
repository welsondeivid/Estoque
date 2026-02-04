package com.stock.raw;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

import java.util.List;

@ApplicationScoped
public class RawMaterialService {

    @Inject
    RawMaterialRepository rawMaterialRepository;

    public List<RawMaterial> listAll(){
        return rawMaterialRepository.listAll();
    }

    public RawMaterial findByCode(String code){
        RawMaterial rawMaterial = rawMaterialRepository.findByCode(code);
        if (rawMaterial == null) throw new NotFoundException("Raw Material not found");

        return rawMaterial;
    }

    @Transactional
    public RawMaterial create(RawMaterial rawMaterial){

        if (rawMaterialRepository.existsByCode(rawMaterial.code))
            throw new WebApplicationException("Raw material already exists", 409);

        rawMaterialRepository.persist(rawMaterial);
        return rawMaterial;
    }

    @Transactional
    public RawMaterial update(String code, RawMaterial newMaterial){

        RawMaterial rawMaterial = rawMaterialRepository.findByCode(code);
        if (rawMaterial == null) throw new NotFoundException("Raw Material not found");

        return rawMaterialRepository.update(rawMaterial, newMaterial);
    }

    @Transactional
    public void delete(String code){

        RawMaterial rawMaterial = rawMaterialRepository.findByCode(code);
        if (rawMaterial == null) throw new NotFoundException("Raw Material not found");

        rawMaterialRepository.delete(rawMaterial);
    }
}
