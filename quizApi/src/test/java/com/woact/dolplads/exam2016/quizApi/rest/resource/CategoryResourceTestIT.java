package com.woact.dolplads.exam2016.quizApi.rest.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.woact.dolplads.exam2016.backend.entity.Category;
import com.woact.dolplads.exam2016.backend.entity.SubCategory;
import com.woact.dolplads.exam2016.dtos.dto.SubCategoryDTO;
import com.woact.dolplads.exam2016.quizApi.rest.CategoryResourceTestBase;
import com.woact.dolplads.exam2016.quizApi.rest.HttpUtil;
import io.restassured.RestAssured;
import io.restassured.http.*;

import com.woact.dolplads.exam2016.dtos.dto.CategoryDto;
import io.restassured.response.ValidatableResponse;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by dolplads on 29/11/2016.
 */
public class CategoryResourceTestIT extends CategoryResourceTestBase {

    private String subCatBasePath = "/quiz/api/subcategories";

    @Test // TODO: 06/12/2016 implement check status
    public void createAndFindById() throws Exception {
        CategoryDto category = new CategoryDto();
        category.name = "someText";

        String location = postCategory(category);
        given().accept(ContentType.JSON).get(location).then().statusCode(200).body("name", is("someText"));

        /*
        when().request(Method.GET, "id/{id}", id).then().assertThat()
                .body("id", equalTo(id)).and().body("name", equalTo(category.name));
                */

    }

    // TODO: 12/12/2016 check the  patch package as well
    @Test
    public void testCreateCategoryRawHttp() throws Exception {
        String message = "POST /quiz/api/categories HTTP/1.1\n";
        message += "Host:localhost:8080\n";
        message += "Accept:application/json\n";
        message += "Content-Type: application/json\n";
        //message += "Accept-Language:no\n";
        message += "Content-Length: 19"; // 2b for ø
        message += "\n\n";
        message += "{\"name\":\"økologi\"}";

        String response = HttpUtil.executeHttpCommand("localhost", 8080, message, "UTF-8");
        System.out.println(response.toString());
        String location = HttpUtil.getHeaderValue("Location", response);
        given().accept(ContentType.JSON).get(location).then().statusCode(200).body("name", is("økologi"));
    }

    @Test
    public void testCreateTwoCategories() throws Exception {
        CategoryDto dto1 = new CategoryDto();
        dto1.name = "name";
        CategoryDto dto2 = new CategoryDto();
        dto2.name = "name2";
        String location1 = postCategory(dto1);
        String location2 = postCategory(dto2);

        ValidatableResponse response = given().contentType(ContentType.JSON).get().then().statusCode(200);
        response.body("size()", is(2));
        response.body("name", hasItems("name"));
        response.body("name", hasItems("name2"));
    }

    @Test
    public void testCreateSubCategory() throws Exception {
        CategoryDto category = new CategoryDto();
        category.name = "name";
        String location1 = postCategory(category);
        category = given().accept(ContentType.JSON).get(location1).
                then().statusCode(200).extract().as(CategoryDto.class);

        SubCategoryDTO subCategoryDTO = new SubCategoryDTO();
        subCategoryDTO.name = "subCategory";
        subCategoryDTO.parentCategory = category;
        String subLocation = postSubCategory(subCategoryDTO);

        ValidatableResponse response = given().accept(ContentType.JSON).get(subLocation).
                then().statusCode(200);

        response.body("name", is("subCategory"));
    }


    /*
    ◦ All categories with false expand: the subcategory should not be present
    ◦ All categories with true expand: the subcategory should be present
    ◦ Specific category with false expand: the subcategory should not be present
    ◦ Specific category with true expand: the subcategory should be present
     */
    @Test
    public void testExpand() throws Exception {
        CategoryDto category = new CategoryDto();
        category.name = "name";
        String location1 = postCategory(category);
        category = given().accept(ContentType.JSON).get(location1).
                then().statusCode(200).extract().as(CategoryDto.class);

        SubCategoryDTO subCategoryDTO = new SubCategoryDTO();
        subCategoryDTO.name = "subCategory";
        subCategoryDTO.parentCategory = category;
        String subLocation = postSubCategory(subCategoryDTO);

        ValidatableResponse response = given().accept(ContentType.JSON).get(subLocation).
                then().statusCode(200);
        response.body("name", is("subCategory"));

        given().queryParam("expand", true).accept(ContentType.JSON).get()
                .then().statusCode(200).body("get(0).subCategoryDTOList.size()", is(1));

        given().queryParam("expand", false).accept(ContentType.JSON).get()
                .then().statusCode(200).body("get(0).subCategoryDTOList.size()", is(0));

        given().queryParam("expand", false).accept(ContentType.JSON).get("{id}", category.id)
                .then().statusCode(200).body("subCategoryDTOList.size()", is(0));

        given().queryParam("expand", true).accept(ContentType.JSON).get("{id}", category.id)
                .then().statusCode(200).body("subCategoryDTOList.size()", is(1));
    }

    @Test
    public void testUpdateCategory() throws Exception {
        CategoryDto category = new CategoryDto();
        category.name = "name";
        String location1 = postCategory(category);

        category = given().accept(ContentType.JSON).get(location1).
                then().statusCode(200).extract().as(CategoryDto.class);

        patchWithMergeJSon(category.id, "{\"name\":\"newName\"}", 204);

        given().accept(ContentType.JSON).get(location1).
                then().statusCode(200).body("name", is("newName"));
    }

    @Test
    public void testGetSubCategories() throws Exception {
        // cat 1
        CategoryDto category1 = new CategoryDto();
        category1.name = "c1";
        String location1 = postCategory(category1);

        // fetch id
        category1 = given().accept(ContentType.JSON).get(location1).
                then().statusCode(200).extract().as(CategoryDto.class);

        // subcat 1
        SubCategoryDTO subCategoryDTO1 = new SubCategoryDTO();
        subCategoryDTO1.name = "subCategory1";
        subCategoryDTO1.parentCategory = category1;
        String subLocation1 = postSubCategory(subCategoryDTO1);

        // cat2
        CategoryDto category2 = new CategoryDto();
        category2.name = "c1";
        String location2 = postCategory(category2);

        // fetch id2
        category2 = given().accept(ContentType.JSON).get(location2).
                then().statusCode(200).extract().as(CategoryDto.class);

        // subcat 2
        SubCategoryDTO subCategoryDTO2 = new SubCategoryDTO();
        subCategoryDTO2.name = "subCategory2";
        subCategoryDTO2.parentCategory = category2;
        String subLocation2 = postSubCategory(subCategoryDTO2);

        // subcat 3
        SubCategoryDTO subCategoryDTO3 = new SubCategoryDTO();
        subCategoryDTO3.name = "subCategory3";
        subCategoryDTO3.parentCategory = category2;
        String subLocation3 = postSubCategory(subCategoryDTO3);

        String oldBase = RestAssured.basePath;
        RestAssured.basePath = subCatBasePath;

        given().accept(ContentType.JSON).get().then()
                .statusCode(200).body("size", is(3));

        given().queryParam("parentId", category1.id).accept(ContentType.JSON).get().then()
                .statusCode(200).body("size", is(1));

        RestAssured.basePath = oldBase;

        given().accept(ContentType.JSON).get("{id}/subcategories", category2.id).then()
                .statusCode(200).body("size", is(2));
    }


    @Ignore
    @Test
    public void invalid_create_throws400() throws Exception {
        CategoryDto category = new CategoryDto();
        given().when().contentType(ContentType.JSON)
                .body(category)
                .post()
                .then()
                .statusCode(400);
    }

    @Ignore
    @Test
    public void findAll() throws Exception {
        int cursize = given().contentType(ContentType.JSON)
                .request(Method.GET).then().statusCode(200).extract().body().as(List.class).size();

        CategoryDto category = new CategoryDto();
        category.name = "catetext";
        postCategory(category);

        given().contentType(ContentType.JSON).get().then().statusCode(200).body("size()", is(cursize + 1));
    }

    @Ignore // TODO: 06/12/2016 fix the contenttype
    @Test
    public void update() throws Exception {
        CategoryDto dto = new CategoryDto();
        dto.name = "tixt";
        String location = postCategory(dto);
        System.out.println("location: " + location);
        dto = given().accept(ContentType.JSON).get(location).then().statusCode(200).extract().as(CategoryDto.class);

        dto.name = "other name";
        putCategory(dto, location);

        given().accept(ContentType.JSON)
                .get(location)
                .then()
                .statusCode(200)
                //.body("name", nullValue())
                .body("name", is(dto.name));
    }

    @Ignore
    @Test
    public void deleteCategory() throws Exception {
        CategoryDto dto = new CategoryDto();
        dto.name = "yo";

        ValidatableResponse response =
                given().contentType(ContentType.JSON)
                        .body(dto)
                        .post()
                        .then()
                        .statusCode(201);

        String id = response.extract().asString();
        System.out.println("the id: " + id);
        String loc = response.extract().header("location");
        System.out.println(loc);

        delete(loc).then().statusCode(204);
        delete(loc).then().statusCode(404);
    }

    private String postCategory(CategoryDto category) {
        return given().contentType(ContentType.JSON)
                .body(category)
                .post()
                .then()
                .statusCode(201)
                .extract().header("location");
    }

    // url == /categories/{id}/subcategories
    private String postSubCategory(SubCategoryDTO subCategoryDTO) {
        return given().contentType(ContentType.JSON)
                .body(subCategoryDTO)
                .post(subCategoryDTO.parentCategory.id + "/subcategories")
                .then()
                .statusCode(201)
                .extract().header("Location");
    }

    private void putCategory(CategoryDto category, String location) {
        given().contentType(ContentType.JSON)
                .body(category)
                .put(location)
                .then()
                .statusCode(204);
    }

    private void patchWithMergeJSon(long id, String jsonBody, int statusCode) {
        given().contentType("application/merge-patch+json")
                .body(jsonBody)
                .patch("{id}", id)
                .then()
                .statusCode(statusCode);
    }

}
