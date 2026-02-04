package com.stock.raw;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/raw-materials")
@Produces(MediaType.APPLICATION_JSON)
public class RawMaterialResource {

    @Inject
    RawMaterialService rawMaterialService;

    @GET
    public List<RawMaterial> listAll(){
        return rawMaterialService.listAll();
    }

    @GET
    @Path("/{code}")
    public RawMaterial findByCode(@PathParam("code") String code){
        return rawMaterialService.findByCode(code);
    }

    @POST
    public Response register(@Valid RawMaterial rawMaterial){
        RawMaterial created = rawMaterialService.create(rawMaterial);
        return Response
                .status(Response.Status.CREATED)
                .entity(created)
                .build();
    }

    @PUT
    @Path("/{code}")
    public RawMaterial update(@PathParam("code") String code, @Valid RawMaterial newMaterial){
         return rawMaterialService.update(code, newMaterial);
    }

    @DELETE
    @Path("/{code}")
    public void delete(@PathParam("code") String code){

        rawMaterialService.delete(code);
    }
}
