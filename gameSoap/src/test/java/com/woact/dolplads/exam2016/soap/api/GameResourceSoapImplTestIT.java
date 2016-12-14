package com.woact.dolplads.exam2016.soap.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.google.gson.Gson;
import no.exam.dolplads.quizApi.dto.QuizDto;
import org.junit.*;
import org.pg6100.soap.client.*;
import org.pg6100.soap.client.AnswerDto;
import org.pg6100.soap.client.GameDto;
import org.pg6100.soap.client.ResultDto;

import javax.xml.ws.BindingProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.*;

/**
 * Created by dolplads on 13/12/2016.
 */
public class GameResourceSoapImplTestIT {
    static GameResource ws;
    private static WireMockServer server;

    @BeforeClass
    public static void initWiremock() throws InterruptedException {
        GameResourceSoapImplService service = new GameResourceSoapImplService();
        ws = service.getGameResourceSoapImplPort();
        String url = "http://localhost:8080/newssoap/GameResourceSoapImpl";

        ((BindingProvider) ws).getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);

        server = new WireMockServer(
                wireMockConfig().port(2222).notifier(new ConsoleNotifier(true))
        );

        List<String> answerss = new ArrayList<>(Arrays.asList("false", "false", "true", "false"));
        QuizDto quizDto = new QuizDto("question", answerss, 2);
        quizDto.id = 1L;
        Gson gson = new Gson();
        String jsonString = gson.toJson(quizDto);

        server.start();
        stubQuizEndpoint(jsonString, "/random");
    }

    @AfterClass
    public static void tearDown() {
        server.stop();
    }

    @Test
    public void testGetRandom() throws Exception {
        GameDto dto = ws.getRandomGame();
        assertEquals(dto.getQuestion(), "question");
    }

    @Test(expected = RuntimeException.class)
    public void testGetRandomFail() throws Exception {
        server.stop();
        System.out.println("stopped server");
        try {
            ws.getRandomGame();

        } catch (Exception e) {
            throw new RuntimeException();
        }

        fail();
    }


    @Test
    public void testPlayGameCorrect() throws Exception {
        GameDto random = ws.getRandomGame();

        AnswerDto answerDto = new AnswerDto();
        answerDto.setQuizId(random.getId());
        answerDto.setCorrectAnswerIndex(2);

        ResultDto result = ws.postAnswer(answerDto);

        assertTrue(result.isCorrect());
    }

    @Test
    public void testPlayGameWrong() throws Exception {
        GameDto random = ws.getRandomGame();

        AnswerDto answerDto = new AnswerDto();
        answerDto.setQuizId(random.getId());
        answerDto.setCorrectAnswerIndex(3);

        ResultDto resultDto = ws.postAnswer(answerDto);
        assertFalse(resultDto.isCorrect());
    }

    @Test(expected = RuntimeException.class)
    public void testServerCrashMiddleOfGame() throws Exception {
        GameDto random = ws.getRandomGame();

        AnswerDto answerDto = new AnswerDto();
        answerDto.setQuizId(random.getId());
        answerDto.setCorrectAnswerIndex(3);
        server.stop();
        try {
            ResultDto resultDto = ws.postAnswer(answerDto);
            assertFalse(resultDto.isCorrect());

        } catch (Exception e) {
            throw new RuntimeException();
        }
        fail();
    }

    private static void stubQuizEndpoint(String json, String relativeUrl) {
        server.stubFor(get(urlEqualTo("/quiz/api/quizzes" + relativeUrl))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json").withBody(json).withStatus(200)));

        server.stubFor(get(urlEqualTo("/quiz/api/quizzes/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json").withBody(json).withStatus(200)));
    }
}