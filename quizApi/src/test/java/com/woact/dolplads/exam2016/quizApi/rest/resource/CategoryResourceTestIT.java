package com.woact.dolplads.exam2016.quizApi.rest.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

import com.woact.dolplads.exam2016.backend.entity.Category;
import com.woact.dolplads.exam2016.quizApi.rest.CategoryResourceTestBase;
import io.restassured.RestAssured;
import io.restassured.http.*;

import com.woact.dolplads.exam2016.quizApi.rest.JBossUtil;
import dto.CategoryDto;
import io.restassured.response.ValidatableResponse;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;


/**
 * Created by dolplads on 29/11/2016.
 */
public class CategoryResourceTestIT extends CategoryResourceTestBase {


    @Test
    public void createAndFindById() throws Exception {
        CategoryDto category = new CategoryDto();
        category.text = "someText";

        String location = postCategory(category);
        given().accept(ContentType.JSON).get(location).then().statusCode(200).body("text", is("someText"));

        /*
        when().request(Method.GET, "id/{id}", id).then().assertThat()
                .body("id", equalTo(id)).and().body("text", equalTo(category.text));
                */

    }

    @Test
    public void invalid_create_throws400() throws Exception {
        CategoryDto category = new CategoryDto();
        given().when().contentType(ContentType.JSON)
                .body(category)
                .post()
                .then()
                .statusCode(400);
    }

    @Test
    public void findAll() throws Exception {
        int cursize = given().contentType(ContentType.JSON)
                .request(Method.GET).then().statusCode(200).extract().body().as(List.class).size();

        CategoryDto category = new CategoryDto();
        category.text = "catetext";
        postCategory(category);

        given().contentType(ContentType.JSON).get().then().statusCode(200).body("size()", is(cursize + 1));
    }

    @Test
    public void update() throws Exception {
        CategoryDto dto = new CategoryDto();
        dto.text = "tixt";
        String location = postCategory(dto);
        System.out.println("location: " + location);
        dto = given().accept(ContentType.JSON).get(location).then().statusCode(200).extract().as(CategoryDto.class);

        dto.text = "other text";
        putCategory(dto, location);

        given().accept(ContentType.JSON)
                .get(location)
                .then()
                .statusCode(200)
                //.body("name", nullValue())
                .body("text", is(dto.text));
    }

    @Test
    public void deleteCategory() throws Exception {
        CategoryDto dto = new CategoryDto();
        dto.text = "yo";

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

    private void putCategory(CategoryDto category, String location) {
        given().contentType(ContentType.JSON)
                .body(category)
                .put(location)
                .then()
                .statusCode(204);
    }
}
