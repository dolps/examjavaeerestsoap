package com.woact.dolplads.exam2016.soap.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.Gson;
import com.netflix.config.ConfigurationManager;
import no.exam.dolplads.quizApi.dto.QuizDto;
import org.apache.commons.configuration.AbstractConfiguration;
import org.junit.*;
import org.pg6100.soap.client.*;
import org.pg6100.soap.client.GameDto;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.ws.BindingProvider;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

/**
 * Created by dolplads on 13/12/2016.
 */
public class GameResourceSoapImplTestIT {
    static GameResource ws;
    private static WireMockServer server;

    @BeforeClass
    public static void initWiremock() throws InterruptedException {
        List<String> answerss = new ArrayList<>(Arrays.asList("false", "false", "true", "false"));
        QuizDto quizDto = new QuizDto("question", answerss, 2);
        quizDto.id = 1L;
        Gson gson = new Gson();
        String x = gson.toJson(quizDto);
        System.out.println("xxx" + x);


        GameResourceSoapImplService service = new GameResourceSoapImplService();
        ws = service.getGameResourceSoapImplPort();
        String url = "http://localhost:8080/newssoap/GameResourceSoapImpl";

        ((BindingProvider) ws).getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);

        server = new WireMockServer(
                wireMockConfig().port(2222).notifier(new ConsoleNotifier(true))
        );

        server.start();
        stubQuizEndpoint(x, "/random");
    }

    @AfterClass
    public static void tearDown() {
        server.stop();
    }

    @Test
    public void testHello() {
        //wireMockServer.start();
        System.out.println("woohoooo soap");
        String hello = ws.getHello();
        assertEquals("hello", hello);
        //wireMockServer.stop();
    }


    @Test
    public void testGetRandom() throws Exception {
        int count = 0;
        while (!server.isRunning() || count > 10) {
            Thread.sleep(300);
            count++;
        }

        System.out.println("made it through the stubbing");
        GameDto dto = ws.getRandomGame();
        assertEquals(dto.getQuestion(), "question");
    }

    private static void stubQuizEndpoint(String json, String relativeUrl) {
        server.stubFor(get(urlEqualTo("/quiz/api/quizzes" + relativeUrl))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json").withBody(json).withStatus(200)));

        server.stubFor(get(urlEqualTo("/" + relativeUrl))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json").withBody(json).withStatus(200)));
        server.stubFor(get(urlEqualTo("/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json").withBody(json).withStatus(200)));
    }

    private void setupHystrix() {
        AbstractConfiguration conf = ConfigurationManager.getConfigInstance();
        conf.setProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", 500);
        conf.setProperty("hystrix.command.default.circuitBreaker.requestVolumeThreshold", 2);
        conf.setProperty("hystrix.command.default.circuitBreaker.errorThresholdPercentage", 50);
        conf.setProperty("hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds", 5000);
    }

}