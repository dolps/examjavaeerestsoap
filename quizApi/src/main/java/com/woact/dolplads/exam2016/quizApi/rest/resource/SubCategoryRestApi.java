package com.woact.dolplads.exam2016.quizApi.rest.resource;

import com.woact.dolplads.exam2016.dtos.dto.SubCategoryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

/**
 * Created by dolplads on 05/12/2016.
 */
@Api(value = "/subcategories", description = "CRUD action for subCategories")
public interface SubCategoryRestApi {
    @ApiOperation("create a subcategory")
    @POST
    Response create(@ApiParam("subcategory to create") SubCategoryDTO subCategory);
}
