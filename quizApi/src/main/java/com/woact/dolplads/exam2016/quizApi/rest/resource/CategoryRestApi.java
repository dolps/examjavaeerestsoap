package com.woact.dolplads.exam2016.quizApi.rest.resource;

import com.woact.dolplads.exam2016.dtos.dto.CategoryDto;
import com.woact.dolplads.exam2016.dtos.dto.SubCategoryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.PATCH;

import javax.ws.rs.*;
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

    @GET
    @ApiOperation("find all categories")
    List<CategoryDto> findAll(@ApiParam("expand optional returns subcategories as well if true")
                              @DefaultValue("false") @QueryParam("expand") boolean expand);


    @ApiOperation(value = "get category by id")
    @GET
    @Path("{id}")
    CategoryDto findById(@PathParam("id") Long id, @DefaultValue("false") @QueryParam("expand") boolean expand);

    @ApiOperation(value = "delete category by id, make sure to delete all its subcategories first")
    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") Long id);

    @PUT
    @Path("{id}")
    void replace(@PathParam("id") Long id, CategoryDto categoryDto);

    @PATCH
    @Path("{id}")
    @Consumes("application/merge-patch+json")
    void partialUpdate(@ApiParam("partial update patch exclude subcategories") @PathParam("id") Long id, String patch);

    /**
     * SubCATEGORY operations:
     */

    @GET
    @Path("{id}/subcategories")
    Response findSubcategoriesById(@PathParam("id") Long id);


    @POST
    @Path("{id}/subcategories")
    @ApiOperation("add a new subcategory")
    Response create(@PathParam("id") Long id, @ApiParam("the subcategory to create") SubCategoryDTO subCategory);

}
