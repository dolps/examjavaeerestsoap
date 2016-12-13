package no.exam.dolplads.quizApi.restApi;

import no.exam.dolplads.quizApi.dto.SubCategoryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by dolplads on 05/12/2016.
 */
@Api(value = "/subcategories", description = "CRUD action for subCategories")
public interface SubCategoryRestApi {
    /*
        // TODO: 12/12/2016 deprecate?
        @Override
        //@Deprecated
        @ApiOperation("create a subcategory")
        @POST
        Response create(@ApiParam("subcategory to create") SubCategoryDTO subCategory);
    */
    @ApiOperation("find all subCategories")
    @GET
    List<SubCategoryDTO> findAll(@ApiParam("filter by parentId")
                                 @QueryParam("parentId") Long id);


    @ApiOperation(value = "get subCategory by id")
    @GET
    @Path("{id}")
    SubCategoryDTO findById(@ApiParam("id of subcategory") @PathParam("id") Long id);
}
