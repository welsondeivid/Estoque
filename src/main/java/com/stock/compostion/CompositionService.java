package com.stock.compostion;

import com.stock.product.ProductService;
import com.stock.raw.RawMaterialService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CompositionService {

    @Inject
    CompositionRepository compositionRepository;
    @Inject
    ProductService productService;
    @Inject
    RawMaterialService rawMaterialService;

    public List<Composition> listByProduct(String productCode) {
        return compositionRepository.findByProduct(productCode);
    }

    @Transactional
    public List<Composition> defineComposition(String productCode, List<CompositionDTO> materials) {
        if (materials.isEmpty()) throw new WebApplicationException("Materials list cannot be empty", 400);

        productService.findByCode(productCode);
        List<Composition> result = new ArrayList<>();

        compositionRepository.deleteByProduct(productCode);

        for (CompositionDTO dto : materials) {

            rawMaterialService.findByCode(dto.rawMaterialCode);

            Composition composition = compositionRepository.findByCodes(productCode, dto.rawMaterialCode);
            if (composition != null) {
                composition.required = dto.required;
                result.add(composition);
                continue;
            }

            CompositionId id = new CompositionId();
            id.productCode = productCode;
            id.rawMaterialCode = dto.rawMaterialCode;

            Composition newComposition = new Composition();
            newComposition.id = id;
            newComposition.required = dto.required;

            compositionRepository.persist(newComposition);
            result.add(newComposition);
        }

        return result;
    }

    @Transactional
    public void delete(String productCode, String rawMaterialCode) {

        Composition composition = compositionRepository.findByCodes(productCode, rawMaterialCode);

        if (composition == null) throw new NotFoundException("Association not found");

        compositionRepository.delete(composition);
    }
}
