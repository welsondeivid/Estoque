package com.stock.production;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductionService {

    @Inject
    ProductionRepository productionRepository;

    public List<ProductionDTO> availableProducts() {

        List<StockDTO> stockData = productionRepository.availableProducts();

        Map<String, ProductionAccumulator> accMap = new java.util.HashMap<>();

        for (StockDTO s : stockData) {

            ProductionAccumulator acc =
                    accMap.computeIfAbsent(s.productCode, k ->
                            new ProductionAccumulator(
                                    s.productCode,
                                    s.productName,
                                    s.unitValue
                            )
                    );

            int possible = s.stock / s.required;
            acc.maxProduction = Math.min(acc.maxProduction, possible);
        }

        return accMap.values().stream()
                .filter(a -> a.maxProduction > 0)
                .sorted((a, b) -> b.totalValue().compareTo(a.totalValue()))
                .map(ProductionAccumulator::toDTO)
                .toList();
    }
}

class ProductionAccumulator {

    String productCode;
    String productName;
    BigDecimal unitValue;
    int maxProduction = Integer.MAX_VALUE;

    ProductionAccumulator(
            String productCode,
            String productName,
            BigDecimal unitValue
    ) {
        this.productCode = productCode;
        this.productName = productName;
        this.unitValue = unitValue;
    }

    BigDecimal totalValue() {
        return unitValue.multiply(BigDecimal.valueOf(maxProduction));
    }

    ProductionDTO toDTO() {
        ProductionDTO dto = new ProductionDTO();
        dto.productCode = productCode;
        dto.productName = productName;
        dto.unitValue = unitValue;
        dto.maxProduction = maxProduction;
        dto.totalValue = totalValue();
        return dto;
    }
}
