package com.woact.dolplads.exam2016.quizApi.rest.resource;

import com.woact.dolplads.exam2016.backend.entity.Category;
import com.woact.dolplads.exam2016.frontend.testUtils.JBossUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by dolplads on 29/11/2016.
 */
public class CategoryResourceTestIT {
    @BeforeClass
    public static void initClass() {
        JBossUtil.waitForJBoss(10);

        // RestAssured configs shared by all the tests
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/quiz/api/categories";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void createAndfindCategories() throws Exception {
        Category category = new Category("catetext");

        int id = given().contentType(ContentType.JSON)
                .body(category)
                .post()
                .then()
                .statusCode(200)
                .extract().as(Integer.class);

        get("{id}", id).then().assertThat().body("id", equalTo(id)).and().body("text", equalTo(category.getText()));
    }

}