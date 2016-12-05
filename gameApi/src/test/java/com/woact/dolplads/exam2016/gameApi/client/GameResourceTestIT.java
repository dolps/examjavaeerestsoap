package com.woact.dolplads.exam2016.gameApi.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.woact.dolplads.exam2016.gameApi.GameApplication;
import com.woact.dolplads.exam2016.gameApi.GameConfiguration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.RestAssured;
import io.restassured.http.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by dolplads on 01/12/2016.
 */
public class GameResourceTestIT {
    @ClassRule
    public static final DropwizardAppRule<GameConfiguration> RULE =
            new DropwizardAppRule<>(GameApplication.class, ResourceHelpers.resourceFilePath("game.yml"));
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

    @BeforeClass
    public static void setupClass() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9000;
        RestAssured.basePath = "/app/api/games";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void hello() throws Exception {
        stubRandomQuizzes("1L");
        //String res = when().request(Method.GET).then().assertThat().extract().as(String.class);
        //System.out.println(res);
    }


    // stubs the endpoint quiz/api/randomQuizzes
    private void stubRandomQuizzes(String json) {
        Long x = 1L;
        stubFor(post(urlEqualTo("/quiz/api/categories"))
                .withHeader("Content-Type", WireMock.equalTo("application/json"))
                //.withRequestBody(WireMock.equalTo(toJson))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(json)
                        .withStatus(200)));

    }

}