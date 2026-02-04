package com.stock.product;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    public List<Product> listAll() {
        return productService.listAll();
    }

    @GET
    @Path("/{code}")
    public Product findByCode(@PathParam("code") String code) {
        return productService.findByCode(code);
    }

    @POST
    public Response register(@Valid Product product) {
        Product created = productService.create(product);
        return Response
                .status(Response.Status.CREATED)
                .entity(created)
                .build();
    }

    @PUT
    @Path("/{code}")
    public Product update(
            @PathParam("code") String code,
            @Valid Product product
    ) {
        return productService.update(code, product);
    }

    @DELETE
    @Path("/{code}")
    public void delete(@PathParam("code") String code) {
        productService.delete(code);
    }
}