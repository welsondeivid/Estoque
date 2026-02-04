package com.stock.production;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/production")
public class ProductionResource {

    @Inject
    ProductionService productionService;

    @GET
    @Path("/available-products")
    public List<ProductionDTO> availableProducts() {
        return productionService.availableProducts();
    }
}
