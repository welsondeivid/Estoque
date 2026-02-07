package com.stock.product;

import com.stock.compostion.CompositionService;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    CompositionService compositionService;

    @Mock
    ProductRepository productRepository;

    @Test
    void createWhenCodeDoesNotExist(){

        Product product = new Product();
        product.code = "Test-00001";
        product.name = "Prod Test";
        product.price = BigDecimal.valueOf(100);

        when(productRepository.existsByCode(product.code)).thenReturn(false);

        Product newProduct = productService.create(product);

        assertEquals(product.code, newProduct.code);
        verify(productRepository).persist(product);
    }

    @Test
    void doesNotCreateWhenExist() {
        Product product = new Product();
        product.code = "Test-00001";
        product.name = "Produto Duplicado";
        product.price = BigDecimal.valueOf(100);

        when(productRepository.existsByCode("Test-00001"))
                .thenReturn(true);

        WebApplicationException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        WebApplicationException.class,
                        () -> productService.create(product)
                );

        assertEquals(409, exception.getResponse().getStatus());
        verify(productRepository, never()).persist(any(Product.class));
    }

    @Test
    void canFindByCode(){
        Product product = new Product();
        product.code = "Test-00001";
        product.name = "Produto Procurado";
        product.price = BigDecimal.valueOf(100);

        when(productRepository.findByCode(product.code)).thenReturn(product);

        Product result = productService.findByCode("Test-00001");

        assertEquals("Test-00001", result.code);
        assertEquals("Produto Procurado", result.name);

        verify(productRepository).findByCode("Test-00001");
    }

    @Test
    void notCanFindByCode(){

        when(productRepository.findByCode("Test-00001")).thenReturn(null);

        NotFoundException exception =
                Assertions.assertThrows(
                        NotFoundException.class,
                        () -> productService.findByCode("Test-00001")
                );

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository).findByCode("Test-00001");
    }

    @Test
    void canDeleteProductAndItsCompositions() {
        Product product = new Product();
        product.code = "Test-00001";

        when(productRepository.findByCode("Test-00001"))
                .thenReturn(product);

        productService.delete("Test-00001");

        verify(productRepository).findByCode("Test-00001");
        verify(compositionService).deleteByProductCode("Test-00001");
        verify(productRepository).delete(product);
    }

    @Test
    void doesNotTryDeleteNonExistingProduct() {
        when(productRepository.findByCode("Test-00001"))
                .thenReturn(null);

        Assertions.assertThrows(
                NotFoundException.class,
                () -> productService.delete("Test-00001")
        );

        verify(productRepository).findByCode("Test-00001");
        verify(compositionService, never()).deleteByProductCode(any());
        verify(productRepository, never()).delete(any());
    }

    @Test
    void canUpdateProductWhenItExists() {

        Product existing = new Product();
        existing.code = "Test-00001";
        existing.name = "Produto Antigo";

        Product updated = new Product();
        updated.code = "Test-00001";
        updated.name = "Produto Novo";

        when(productRepository.findByCode("Test-00001"))
                .thenReturn(existing);

        when(productRepository.update(existing, updated))
                .thenReturn(updated);

        Product result = productService.update("Test-00001", updated);

        assertEquals("Produto Novo", result.name);

        verify(productRepository).findByCode("Test-00001");
        verify(productRepository).update(existing, updated);
    }

    @Test
    void doesNotTryUpdateNonExistingProduct() {
        Product updated = new Product();
        updated.name = "Novo";

        when(productRepository.findByCode("Test-00001"))
                .thenReturn(null);

        Assertions.assertThrows(
                NotFoundException.class,
                () -> productService.update("Test-00001", updated)
        );

        verify(productRepository).findByCode("Test-00001");
        verify(productRepository, never()).update(any(Product.class), any(Product.class));
    }

}
