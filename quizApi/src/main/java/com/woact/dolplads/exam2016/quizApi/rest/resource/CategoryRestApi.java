package com.woact.dolplads.exam2016.quizApi.rest.resource;

import com.woact.dolplads.exam2016.dtos.dto.CategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by dolplads on 30/11/2016.
 */
@Api(value = "/categories", description = "CRUD action for categories")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON)
public interface CategoryRestApi {

    @POST
    @ApiOperation("create a category")
    Response create(@ApiParam("the category to create") CategoryDto category);

    @ApiOperation("Find category by id")
    Response deprecatedFindById(@ApiParam("numeric id represent pk of category") Long id);


    @GET
    @ApiOperation("findAll all categories")
    List<CategoryDto> findAll();

    @ApiOperation(value = "get with text plain")
    @GET
    @Path("{id}")
    @Produces({"application/vnd.github.v2+json; charset=UTF-8", "application/vnd.pg6100.news+json; charset=UTF-8; version=1"})
    CategoryDto findById(@PathParam("id") Long id);


    @ApiOperation(value = "get with text plain")
    @Produces({"application/vnd.pg6100.news+json; charset=UTF-8; version=1"})
    @Consumes({"application/vnd.pg6100.news+json; charset=UTF-8; version=1"})
    @GET
    @Path("id/id/{id}")
    String findByIdStrings(@PathParam("id") Long id);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") Long id);

    @PUT
    @Path("{id}")
    void update(@PathParam("id") Long id, CategoryDto categoryDto);

    @ApiOperation("deprecatedUpdate a category")
    public Response deprecatedUpdate(@ApiParam("numeric id represent pk of category") @NotNull(message = "id should not be null") Long id,
                                     @ApiParam("The updated category, cannot change id") CategoryDto categoryDto);
}
