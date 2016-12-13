package no.exam.dolplads.gameApi;

import com.google.gson.Gson;
import no.exam.dolplads.quizApi.dto.QuizDto;
import no.exam.dolplads.gameApi.dto.AnswerDto;
import no.exam.dolplads.gameApi.dto.GameDto;
import no.exam.dolplads.gameApi.dto.ResultDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by dolplads on 13/12/2016.
 */
public class GameApplicationTestBase {
    @BeforeClass
    public static void setupClass() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9000;
        RestAssured.basePath = "/game/api/games";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void testGetRandom() throws Exception {
        List<String> answerss = new ArrayList<>(Arrays.asList("false", "false", "true", "false"));
        QuizDto quizDto = new QuizDto("question", answerss, 2);
        quizDto.id = 1L;
        stubQuizEndpoint(new Gson().toJson(quizDto), "/random");

        GameDto dto = given().accept(ContentType.JSON).get("/random")
                .then().statusCode(200).body("question", is("question"))
                .extract().as(GameDto.class);

        System.out.println(dto.question);

    }

    @Test
    public void testGetRandomFail() throws Exception {
        given().accept(ContentType.JSON).get("/random")
                .then().statusCode(500);
    }

    @Test
    public void testPlayGameCorrect() throws Exception {
        List<String> answerss = new ArrayList<>(Arrays.asList("false", "false", "true", "false"));
        QuizDto quizDto = new QuizDto("question", answerss, 2);
        quizDto.id = 1L;
        stubQuizEndpoint(new Gson().toJson(quizDto), "/random");

        GameDto dto = given().accept(ContentType.JSON).get("/random")
                .then().statusCode(200).body("question", is("question"))
                .extract().as(GameDto.class);
        AnswerDto answerDto = new AnswerDto();
        answerDto.quizId = dto.id;
        answerDto.correctAnswerIndex = 2;

        stubQuizEndpoint(new Gson().toJson(quizDto), "/" + answerDto.quizId);
        ResultDto resultDto = postAnswer(answerDto);
        assertTrue(resultDto.correct);
    }

    @Test
    public void testPlayGameWrong() throws Exception {
        List<String> answerss = new ArrayList<>(Arrays.asList("false", "false", "true", "false"));
        QuizDto quizDto = new QuizDto("question", answerss, 2);
        quizDto.id = 1L;
        stubQuizEndpoint(new Gson().toJson(quizDto), "/random");

        GameDto dto = given().accept(ContentType.JSON).get("/random")
                .then().statusCode(200).body("question", is("question"))
                .extract().as(GameDto.class);
        AnswerDto answerDto = new AnswerDto();
        answerDto.quizId = dto.id;
        answerDto.correctAnswerIndex = 3;

        stubQuizEndpoint(new Gson().toJson(quizDto), "/" + answerDto.quizId);
        ResultDto resultDto = postAnswer(answerDto);
        assertFalse(resultDto.correct);
    }


    private void stubQuizEndpoint(String json, String relativeUrl) {
        stubFor(get(urlEqualTo("/quiz/api/quizzes" + relativeUrl))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json").withBody(json).withStatus(200)));
    }


    private ResultDto postAnswer(AnswerDto answerDto) {
        return given().contentType(ContentType.JSON)
                .body(answerDto)
                .post()
                .then()
                .statusCode(200)                // thought 201 to be a little confusing here
                .extract().as(ResultDto.class);
    }

}
