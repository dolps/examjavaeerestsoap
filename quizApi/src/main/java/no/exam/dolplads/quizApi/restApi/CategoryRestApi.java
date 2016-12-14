package no.exam.dolplads.quizApi.restApi;

import no.exam.dolplads.quizApi.dto.CategoryDto;
import no.exam.dolplads.quizApi.dto.SubCategoryDTO;
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
    CategoryDto findById(@ApiParam("id of category") @PathParam("id") Long id,
                         @ApiParam("boolean option to get subcategories with response object")
                         @DefaultValue("false") @QueryParam("expand") boolean expand);

    @ApiOperation(value = "delete category by id, make sure to delete all its subcategories first")
    @DELETE
    @Path("{id}")
    void delete(@ApiParam("id of the category") @PathParam("id") Long id);

    @ApiOperation("Merge patch a category")
    @PATCH
    @Path("{id}")
    @Consumes("application/merge-patch+json")
    void partialUpdate(@ApiParam("partial update patch exclude subcategories") @PathParam("id") Long id,
                       @ApiParam("the applied patch, make sure its valid") String patch);

    /**
     * SubCATEGORY operations:
     */

    @ApiOperation("find subcategories by id")
    @GET
    @Path("{id}/subcategories")
    Response findSubcategoriesById(@ApiParam("id of the subcategory") @PathParam("id") Long id);

    @ApiOperation("add a new subcategory to an existing category")
    @POST
    @Path("{id}/subcategories")
    Response create(@ApiParam("id of the category") @PathParam("id") Long id, @ApiParam("the subcategory to create") SubCategoryDTO subCategory);

}
