package com.stock.product;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public Product findByCode(String code) {
        return find("code", code).firstResult();
    }

    public boolean existsByCode(String code) {
        return count("code", code) > 0;
    }

    public Product update (Product product, Product new_product){
        product.name = new_product.name;
        product.price = new_product.price;

        return product;
    }
}