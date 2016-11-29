package com.woact.dolplads.exam2016.quizApi.rest.resource;

import com.woact.dolplads.exam2016.frontend.testUtils.JBossUtil;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import org.junit.BeforeClass;
import org.junit.Test;


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
        when().request(Method.GET).then().statusCode(200);
        /*
        Category category = new Category("catetext");

        int id = given().contentType(ContentType.JSON)
                .body(category)
                .post()
                .then()
                .statusCode(200)
                .extract().as(Integer.class);

        get("{id}", id).then().assertThat().body("id", equalTo(id)).and().body("text", equalTo(category.getText()));

        */
    }

}