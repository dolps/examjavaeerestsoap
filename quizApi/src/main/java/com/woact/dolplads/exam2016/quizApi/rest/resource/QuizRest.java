package com.woact.dolplads.exam2016.quizApi.rest.resource;

import com.woact.dolplads.exam2016.dtos.collection.ListDto;
import com.woact.dolplads.exam2016.dtos.dto.CategoryDto;
import com.woact.dolplads.exam2016.dtos.dto.QuizDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by dolplads on 12/12/2016.
 */
@Api(value = "/quizzes", description = "CRUD action for quizzes")
@Path("/quizzes")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON)
public interface QuizRest {

    @POST
    @ApiOperation("create a quiz")
    Response create(@ApiParam("the quiz to create") QuizDto quiz);

    @GET
    @ApiOperation("find all quizzes")
    ListDto<QuizDto> findAll(
            @DefaultValue("0") @QueryParam("offset") int offset,
            @DefaultValue("10") @QueryParam("limit") int limit,
            @DefaultValue("-1") @QueryParam("filter") long subCategoryId);


    @ApiOperation(value = "get quiz by id")
    @GET
    @Path("{id}")
    QuizDto findById(@PathParam("id") Long id);

    @ApiOperation(value = "delete quiz by id")
    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") Long id);

    @PUT
    @Path("{id}")
    void replace(@PathParam("id") Long id, CategoryDto categoryDto);

    @PATCH
    @Path("{id}")
    @Consumes("application/merge-patch+json")
    void partialUpdate(@ApiParam("partial update") @PathParam("id") Long id, String patch);

    @GET
    @Path("/random")
    QuizDto findRandomQuiz();
}
