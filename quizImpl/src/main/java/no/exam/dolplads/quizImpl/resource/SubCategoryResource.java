package no.exam.dolplads.quizImpl.resource;

import com.google.common.base.Throwables;
import no.exam.dolplads.entities.service.SubCategoryEJB;
import no.exam.dolplads.quizApi.dto.SubCategoryDTO;
import no.exam.dolplads.quizApi.restApi.SubCategoryRestApi;
import no.exam.dolplads.quizImpl.transformers.CategoryConverter;

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


    /*
    @Override
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

        return Response.created(categoryUri).build();
    }
    */

    @Override
    public List<SubCategoryDTO> findAll(Long id) {
        if (id != null) {
            return CategoryConverter.transformAllSubs(subCategoryEJB.findAllByParentId(id));
        }

        return CategoryConverter.transformAllSubs(subCategoryEJB.findAll());
    }

    @Override
    public SubCategoryDTO findById(Long id) {
        return CategoryConverter.transformSub(subCategoryEJB.findById(id));
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
