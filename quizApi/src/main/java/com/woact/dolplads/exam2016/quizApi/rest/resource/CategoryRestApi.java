package com.woact.dolplads.exam2016.quizApi.rest.resource;

import dto.CategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by dolplads on 30/11/2016.
 */
@Api(value = "/categories", description = "CRUD action for categories")
public interface CategoryRestApi {

    @ApiOperation("create a category")
    Response create(@ApiParam("the category to create") CategoryDto category);

    @ApiOperation("Find category by id")
    Response deprecatedFindById(@ApiParam("numeric id represent pk of category") Long id);


    @ApiOperation("findAll all categories")
    List<CategoryDto> findAll();

    @GET
    @Path("{id}")
    CategoryDto findById(@PathParam("id") Long id);

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
