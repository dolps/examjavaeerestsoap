package com.woact.dolplads.exam2016.quizApi.rest.resource;

import com.google.common.base.Throwables;
import com.woact.dolplads.exam2016.backend.entity.Category;
import com.woact.dolplads.exam2016.backend.service.SubCategoryEJB;
import com.woact.dolplads.exam2016.dtos.dto.CategoryDto;
import com.woact.dolplads.exam2016.dtos.dto.SubCategoryDTO;
import com.woact.dolplads.exam2016.quizApi.rest.transformers.CategoryConverter;

import javax.ejb.EJB;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * Created by dolplads on 05/12/2016.
 */
@Path("subcategories")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON)
public class SubCategoryResource implements SubCategoryRestApi {
    @EJB
    private SubCategoryEJB subCategoryEJB;
    @Context
    UriInfo uriInfo;

    @Override
    @POST
    public Response create(SubCategoryDTO subCategory) {
        if (subCategory == null)
            throw new BadRequestException("resource is null");
        Long id = null;
        try {
            id = subCategoryEJB.create(CategoryConverter.transformSub(subCategory)).getId();
        } catch (Exception e) {
            throw wrapException(e);
        }

        URI categoryUri = uriInfo.getAbsolutePathBuilder().path("" + id).build();
        //return Response.created(uriInfo.getBaseUriBuilder().path("/categories/" + id).build()).build();
        return Response.created(categoryUri).build();
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
