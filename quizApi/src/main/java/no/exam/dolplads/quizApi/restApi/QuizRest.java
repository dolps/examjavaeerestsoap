package no.exam.dolplads.quizApi.restApi;

import no.exam.dolplads.quizApi.dto.ListDto;
import no.exam.dolplads.quizApi.dto.CategoryDto;
import no.exam.dolplads.quizApi.dto.QuizDto;
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
            @ApiParam("marks the start of request")
            @DefaultValue("0") @QueryParam("offset") int offset,
            @ApiParam("limits result")
            @DefaultValue("10") @QueryParam("limit") int limit,
            @ApiParam("return only the once by a given subCategory")
            @DefaultValue("-1") @QueryParam("filter") long subCategoryId);


    @ApiOperation(value = "get quiz by id")
    @GET
    @Path("{id}")
    QuizDto findById(@ApiParam("the id of the quiz") @PathParam("id") Long id);

    @ApiOperation(value = "delete quiz by id")
    @DELETE
    @Path("{id}")
    void delete(@ApiParam("id of quiz") @PathParam("id") Long id);

    @ApiOperation("partial update of category")
    @PATCH
    @Path("{id}")
    @Consumes("application/merge-patch+json")
    void partialUpdate(@ApiParam("partial update") @PathParam("id") Long id, @ApiParam("applied patch") String patch);

    @ApiOperation("find a random quiz, make sure to have created some first")
    @GET
    @Path("/random")
    QuizDto findRandomQuiz();
}
