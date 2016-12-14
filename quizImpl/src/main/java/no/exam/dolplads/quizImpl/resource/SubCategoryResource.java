package no.exam.dolplads.quizImpl.resource;

import com.google.common.base.Throwables;
import no.exam.dolplads.entities.entity.SubCategory;
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

    @Override
    public List<SubCategoryDTO> findAll(Long id) {
        if (id != null) {
            return CategoryConverter.transformAllSubs(subCategoryEJB.findAllByParentId(id));
        }

        return CategoryConverter.transformAllSubs(subCategoryEJB.findAll());
    }

    @Override
    public SubCategoryDTO findById(Long id) {
        SubCategory subCategory = subCategoryEJB.findById(id);
        if (subCategory == null) {
            throw new WebApplicationException("resource not found", 404);
        }
        return CategoryConverter.transformSub(subCategory);
    }
}
