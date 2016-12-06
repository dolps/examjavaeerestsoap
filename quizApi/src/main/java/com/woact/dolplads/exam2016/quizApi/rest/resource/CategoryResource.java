package com.woact.dolplads.exam2016.quizApi.rest.resource;

import com.google.common.base.Throwables;
import com.woact.dolplads.exam2016.backend.entity.Category;
import com.woact.dolplads.exam2016.backend.service.CategoryEJB;
import com.woact.dolplads.exam2016.quizApi.rest.transformers.CategoryConverter;
import com.woact.dolplads.exam2016.dtos.dto.CategoryDto;

import javax.ejb.EJB;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * Created by dolplads on 29/11/2016.
 * <p>
 * querying
 */ // Hibernate.initialize(entity.getAddresses());
// http://localhost:8080/pg5100_exam/api/categories/1
@Path("/categories")
public class CategoryResource implements CategoryRestApi {

    public void query() {
        Response response =
                ClientBuilder.newClient().target("http://www.google.com/book").request(MediaType.APPLICATION_JSON).get();
        String body = response.readEntity(String.class);

        CategoryDto dto = ClientBuilder.newClient().target("link").request().get(CategoryDto.class);
    }

    @EJB
    private CategoryEJB categoryEJB;
    @Context
    UriInfo uriInfo;

    @Override
    public Response create(CategoryDto category) {
        if (category == null)
            throw new BadRequestException("resource is null");
        Long id = null;
        try {
            id = categoryEJB.createCategory(CategoryConverter.transform(category)).getId();
        } catch (Exception e) {
            throw wrapException(e);
        }

        URI categoryUri = uriInfo.getAbsolutePathBuilder().path("" + id).build();
        //return Response.created(uriInfo.getBaseUriBuilder().path("/categories/" + id).build()).build();
        return Response.created(categoryUri).build();
    }

    @Override
    public List<CategoryDto> findAll() {
        return CategoryConverter.transform(categoryEJB.findAll());
    }

    @Override
    public CategoryDto findById(Long id) {
        Category c = categoryEJB.findById(id);
        if (c == null) {
            //throw new WebApplicationException("Cannot find news with id: " + id, 404);
            throw new NotFoundException("resource not found");
        }

        return CategoryConverter.transform(c);
    }

    @Override
    public String findByIdStrings(Long id) {
        return "the id: " + id;
    }

    @Override
    public void delete(Long id) {
        Category c = categoryEJB.findById(id);
        if (c == null) {
            throw new WebApplicationException("resource could not be found", Response.Status.NOT_FOUND);
        }
        categoryEJB.removeCategory(c);
    }

    @Override
    @PUT
    @Path("{id}")
    public void update(@PathParam("id") Long id, CategoryDto categoryDto) {
        if (!id.equals(categoryDto.id)) {
            throw new WebApplicationException("ids dont match, not allowed to change id on deprecatedUpdate", 409);//Response.Status.CONFLICT
        }
        Category found = categoryEJB.findById(id);
        if (found == null) {
            throw new WebApplicationException("could not findAll resource with id: " + id, 404);
        }

        try {
            categoryEJB.update(CategoryConverter.transform(categoryDto));
        } catch (Exception e) {
            throw wrapException(e);
        }
    }

    private WebApplicationException wrapException(Exception e) throws WebApplicationException {

        Throwable cause = Throwables.getRootCause(e);
        if (cause instanceof ConstraintViolationException) {
            return new WebApplicationException("Invalid constraints on input: " + cause.getMessage(), 400);
        } else {
            return new WebApplicationException("Internal error", 500);
        }
    }

    @Override
    @PUT
    @Path("id/{id}")
    public Response deprecatedUpdate(@PathParam("id") Long id, CategoryDto categoryDto) {
        return Response.status(301).location(UriBuilder.fromUri("categories/" + id).build()).build();
    }

    @Override
    @GET
    @Path("/id/{id}")
    public Response deprecatedFindById(@PathParam("id") Long id) {
        return Response.status(301).location(UriBuilder.fromUri("categories/" + id).build()).build();
    }

}
