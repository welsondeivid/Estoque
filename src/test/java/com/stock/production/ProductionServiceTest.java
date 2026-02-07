package com.stock.production;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductionServiceTest {

    @InjectMocks
    ProductionService productionService;

    @Mock
    ProductionRepository productionRepository;

    @Test
    void isOrderDescending() {

        StockDTO productA = new StockDTO(
                "A",
                "Produto A",
                BigDecimal.valueOf(10),
                100,
                10
        );

        StockDTO productB = new StockDTO(
                "B",
                "Produto B",
                BigDecimal.valueOf(50),
                100,
                5
        );

        when(productionRepository.availableProducts())
                .thenReturn(List.of(productA, productB));

        List<ProductionDTO> result =
                productionService.availableProducts();

        assertEquals(2, result.size());
        assertEquals("B", result.get(0).productCode);
        assertEquals("A", result.get(1).productCode);
    }

    @Test
    void hasImpossibleProducts() {
        StockDTO noStock = new StockDTO(
                "A",
                "Produto Sem Estoque",
                BigDecimal.valueOf(100),
                0,
                10
        );

        StockDTO hasStock = new StockDTO(
                "B",
                "Produto Produzível",
                BigDecimal.valueOf(50),
                100,
                10
        );

        when(productionRepository.availableProducts())
                .thenReturn(List.of(noStock, hasStock));

        List<ProductionDTO> result =
                productionService.availableProducts();

        assertEquals(1, result.size());
        assertEquals("B", result.get(0).productCode);
    }

    @Test
    void hasInferiorLimitByProduces() {
        StockDTO mp1 = new StockDTO(
                "A",
                "Produto A",
                BigDecimal.valueOf(20),
                100,
                10
        );

        StockDTO mp2 = new StockDTO(
                "A",
                "Produto A",
                BigDecimal.valueOf(20),
                15,
                5
        );

        when(productionRepository.availableProducts())
                .thenReturn(List.of(mp1, mp2));

        List<ProductionDTO> result =
                productionService.availableProducts();

        assertEquals(1, result.size());
        assertEquals("A", result.get(0).productCode);
        assertEquals(3, result.get(0).maxProduction);
    }
}