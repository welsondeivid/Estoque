package com.stock.product;

import com.stock.compostion.CompositionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    @Inject
    CompositionService compositionService;

    public List<Product> listAll() {
        return productRepository.listAll();
    }

    public Product findByCode(String code) {
        Product product = productRepository.findByCode(code);
        if (product == null) throw new NotFoundException("Product not found");

        return product;
    }

    @Transactional
    public Product create(Product product) {
        if (productRepository.existsByCode(product.code))
            throw new WebApplicationException("Product code already exists", 409);

        productRepository.persist(product);
        return product;
    }

    @Transactional
    public Product update(String code, Product new_product) {
        Product product = productRepository.findByCode(code);
        if (product == null) throw new NotFoundException("Product not found");

        return productRepository.update(product, new_product);
    }

    @Transactional
    public void delete(String code) {
        Product product = productRepository.findByCode(code);
        if (product == null) throw new NotFoundException("Product not found");

        compositionService.deleteByProductCode(code);

        productRepository.delete(product);
    }
}
