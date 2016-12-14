package no.exam.dolplads.quizImpl.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

import no.exam.dolplads.entities.entity.Category;
import no.exam.dolplads.entities.entity.SubCategory;
import no.exam.dolplads.entities.service.CategoryEJB;
import no.exam.dolplads.entities.service.SubCategoryEJB;
import no.exam.dolplads.quizApi.dto.SubCategoryDTO;
import no.exam.dolplads.quizApi.restApi.CategoryRestApi;
import no.exam.dolplads.quizImpl.transformers.CategoryConverter;
import no.exam.dolplads.quizApi.dto.CategoryDto;

import javax.ejb.EJB;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;


@Path("/categories")
public class CategoryResource implements CategoryRestApi {
    @EJB
    private CategoryEJB categoryEJB;
    @EJB
    private SubCategoryEJB subCategoryEJB;
    @Context
    UriInfo uriInfo;

    @Override
    public Response create(CategoryDto category) {
        if (category == null)
            throw new BadRequestException("resource is null");
        Long id;
        try {
            id = categoryEJB.create(CategoryConverter.transform(category)).getId();
        } catch (Exception e) {
            throw wrapException(e);
        }

        URI categoryUri = uriInfo.getAbsolutePathBuilder().path("" + id).build();

        return Response.created(categoryUri).build();
    }

    @Override
    public List<CategoryDto> findAll(boolean expand) {
        if (expand) {
            return CategoryConverter.transform(categoryEJB.findAllExpanded(), true);
        } else {
            return CategoryConverter.transform(categoryEJB.findAll(), false);
        }
    }

    @Override
    public CategoryDto findById(Long id, boolean expand) {
        if (expand) {
            return CategoryConverter.transform(categoryEJB.findByIdExpanded(id), true);
        }

        return CategoryConverter.transform(categoryEJB.findById(id), false);
    }

    @Override
    public void delete(Long id) {
        Category c = categoryEJB.findById(id);
        if (c == null) {
            throw new WebApplicationException("resource could not be found", Response.Status.NOT_FOUND);
        }
        categoryEJB.remove(c);
    }

    @Override
    public void replace(Long id, CategoryDto categoryDto) {
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

    @Override
    public void partialUpdate(Long id, String patch) {
        Category c = categoryEJB.findById(id);
        if (c == null) {
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

        String newName = c.getName();
        if (jsonNode.has("name")) {
            JsonNode nameNode = jsonNode.get("name");
            if (nameNode.isNull()) {
                newName = null;
            } else if (nameNode.isTextual()) {
                newName = nameNode.asText();
            } else {
                throw new WebApplicationException("Invalid JSON. Non-string name", 400);
            }

            c.setName(newName);
            categoryEJB.update(c);
        }
    }

    @Override
    public Response findSubcategoriesById(Long id) {
        return Response.status(301).location(UriBuilder.fromUri("subcategories?parentId=" + id).build()).build();
    }

    @Override
    public Response create(Long id, SubCategoryDTO subCategory) {
        SubCategory subCat = CategoryConverter.transformSub(subCategory);
        subCat = subCategoryEJB.create(subCat);
        System.out.println("testtest" + subCat.getName() + " " + subCat.getId());
        URI categoryUri = uriInfo.getBaseUriBuilder().path("/subcategories/" + subCat.getId()).build();

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
