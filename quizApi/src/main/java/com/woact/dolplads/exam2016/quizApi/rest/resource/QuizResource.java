package com.woact.dolplads.exam2016.quizApi.rest.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.woact.dolplads.exam2016.backend.entity.Quiz;
import com.woact.dolplads.exam2016.backend.entity.SubCategory;
import com.woact.dolplads.exam2016.backend.service.QuizEJB;
import com.woact.dolplads.exam2016.backend.service.SubCategoryEJB;
import com.woact.dolplads.exam2016.dtos.collection.ListDto;
import com.woact.dolplads.exam2016.dtos.dto.CategoryDto;
import com.woact.dolplads.exam2016.dtos.dto.QuizDto;
import com.woact.dolplads.exam2016.dtos.hal.HalLink;
import com.woact.dolplads.exam2016.quizApi.rest.transformers.QuizConverter;

import javax.ejb.EJB;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Created by dolplads on 12/12/2016.
 */
@Path("/quizzes")
public class QuizResource implements QuizRest {
    @EJB
    private QuizEJB quizEJB;
    @EJB
    private SubCategoryEJB categoryEJB;

    @Context
    UriInfo uriInfo;


    @Override
    public Response create(QuizDto quiz) {
        if (quiz == null) {
            throw new BadRequestException("resource is null");
        }
        SubCategory persisted = categoryEJB.findById(quiz.subCategoryId);
        if (persisted == null) {
            throw new WebApplicationException("subcategory does not exist", 404);
        }
        Long id;
        try {
            id = quizEJB.create(QuizConverter.transform(quiz)).getId();
        } catch (Exception e) {
            throw wrapException(e);
        }

        URI categoryUri = uriInfo.getAbsolutePathBuilder().path("" + id).build();
        return Response.created(categoryUri).build();
    }

    // TODO: 12/12/2016 CHECK INPUT
    // // TODO: 12/12/2016 Check if wrong to remove limit
    @Override
    public ListDto<QuizDto> findAll(int offset, int limit, long subCategoryId) {
        List<Quiz> quizList = quizEJB.findAll(offset, limit, subCategoryId);


        System.out.println("do we even get here?");

        UriBuilder builder = uriInfo.getBaseUriBuilder()
                .path("/quizzes")
                .queryParam("limit", limit);
        if (subCategoryId != -1) {
            builder.queryParam("filter", subCategoryId);
        }

        ListDto<QuizDto> dto = QuizConverter.transform(quizList, offset, limit);

        dto._links.self = new HalLink(builder.clone().queryParam("offset", offset).build().toString());

        if (!quizList.isEmpty() && offset > 0) {
            dto._links.previous = new HalLink(builder.clone()
                    .queryParam("offset", Math.max(offset - limit, 0))
                    .build().toString()
            );
        }
        if (offset + limit < quizList.size()) {
            dto._links.next = new HalLink(builder.clone()
                    .queryParam("offset", offset + limit)
                    .build().toString()
            );
        }

        return dto;
    }

    @Override
    public QuizDto findById(Long id) {
        Quiz quiz = quizEJB.findById(id);
        if (quiz == null) {
            throw new WebApplicationException("resource not found", 404);
        }
        return QuizConverter.transform(quiz);
    }

    @Override
    public void delete(Long id) {
        Quiz quiz = quizEJB.findById(id);
        if (quiz == null) {
            throw new WebApplicationException("resource not found", 404);
        }
        quizEJB.remove(quiz);
    }

    @Override
    public void replace(Long id, CategoryDto categoryDto) {

    }

    @Override
    public void partialUpdate(Long id, String patch) {

        Quiz quiz = quizEJB.findById(id);
        if (quiz == null) {
            throw new WebApplicationException("resource not found", 404);
        }

        ObjectMapper jackson = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = jackson.readValue(patch, JsonNode.class);
        } catch (Exception e) {
            throw new WebApplicationException("Invalid JSON data as input: " + e.getMessage(), 400);
        }
        if (jsonNode.has("id")) {
            throw new WebApplicationException(
                    "Cannot modify the counter id from " + id + " to " + jsonNode.get("id"), 409);
        }

        String question = quiz.getQuestion();
        if (jsonNode.has("question")) {
            JsonNode questionNode = jsonNode.get("question");
            if (questionNode.isNull()) {
                question = null;
            } else if (questionNode.isTextual()) {
                question = questionNode.asText();
            } else {
                throw new WebApplicationException("Invalid JSON. Non-string question", 400);
            }
        }
        int correctIndex = quiz.getCorrectIndex();
        if (jsonNode.has("correctIndex")) {
            JsonNode correctNode = jsonNode.get("correctIndex");
            if (correctNode.isNull()) {
                correctIndex = -1; // my representation of null in the entity
            } else if (correctNode.isInt()) {
                correctIndex = correctNode.asInt();
            } else {
                throw new WebApplicationException("Invalid JSON. Non- integer index", 400);
            }
        }

        quiz.setCorrectIndex(correctIndex);
        quiz.setQuestion(question);

        quizEJB.update(quiz);
    }

    @Override
    public QuizDto findRandomQuiz() {

        Quiz quiz = quizEJB.findRandom();
        if (quiz == null) {
            throw new WebApplicationException("no quizzes found", 404);
        }

        return QuizConverter.transform(quiz);
    }


    private WebApplicationException wrapException(Exception e) throws WebApplicationException {

        Throwable cause = Throwables.getRootCause(e);
        if (cause instanceof ConstraintViolationException) {
            return new WebApplicationException("Invalid constraints on input: " + cause.getMessage(), 400);
        } else {
            return new WebApplicationException("Internal error", 500);
        }
    }
}