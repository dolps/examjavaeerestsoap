package com.woact.dolplads.exam2016.quizApi.rest.resource;

import com.woact.dolplads.exam2016.backend.entity.Category;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.woact.dolplads.exam2016.quizApi.rest.JBossUtil;
import io.restassured.RestAssured;
import io.restassured.http.*;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Created by dolplads on 29/11/2016.
 */
public class CategoryResourceTestIT {
    @BeforeClass
    public static void initClass() {
        JBossUtil.waitForJBoss(10);

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/quiz/api/categories";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void createAndfindCategories() throws Exception {
        when().request(Method.GET).then().statusCode(200);

        Category category = new Category("catetext");

        int id = given().contentType(ContentType.JSON)
                .body(category)
                .post()
                .then()
                .statusCode(200)
                .extract().as(Integer.class);

        when().request(Method.GET,"{id}",id).then().assertThat().body("id",equalTo(id));
    }

}