package com.woact.dolplads.exam2016.quizApi.rest.resource;

import com.woact.dolplads.exam2016.backend.entity.Category;
import com.woact.dolplads.exam2016.backend.service.CategoryEJB;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by dolplads on 29/11/2016.
 */
// http://localhost:8080/pg5100_exam/api/categories/1
@Api(value = "/categories", description = "CRUD action for categories")
@Path("/categories")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {
    @EJB
    private CategoryEJB categoryEJB;


    @POST
    @ApiOperation("create a categories")
    public Long createCategory(Category category) {
        return categoryEJB.createCategory(category).getId();
    }

    @GET
    @Path("/{id}")
    @ApiOperation("Find category by id")
    public Category findCategories(@PathParam("id") Long id) {
        return categoryEJB.findById(id);
    }

    @GET
    @ApiOperation("find all cattises")
    public List<Category> findCategories() {
        return categoryEJB.findAll();
    }
}
