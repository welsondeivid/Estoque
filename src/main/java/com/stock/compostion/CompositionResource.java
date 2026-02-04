package com.stock.compostion;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/product/{productCode}/raw-material")
public class CompositionResource {

    @Inject
    CompositionService compositionService;
    @GET
    public List<Composition> listRawMaterialsOfProduct(@PathParam("productCode") String productCode){
        return compositionService.listByProduct(productCode);
    }

    @POST
    public Response defineComposition(@PathParam("productCode") String productCode, @Valid List<CompositionDTO> materials) {

        List<Composition> compositions =
                compositionService.defineComposition(productCode, materials);

        return Response.status(Response.Status.CREATED)
                .entity(compositions)
                .build();
    }

    @DELETE
    @Path("/{rawMaterialCode}")
    public Response delete(
            @PathParam("productCode") String productCode,
            @PathParam("rawMaterialCode") String rawMaterialCode
    ) {
        compositionService.delete(productCode, rawMaterialCode);
        return Response.noContent().build();
    }
}
