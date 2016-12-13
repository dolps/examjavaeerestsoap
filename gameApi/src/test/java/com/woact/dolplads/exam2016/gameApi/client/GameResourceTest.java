package com.woact.dolplads.exam2016.gameApi.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.Gson;
import com.woact.dolplads.exam2016.dtos.dto.QuizDto;
import com.woact.dolplads.exam2016.gameApi.GameApplication;
import com.woact.dolplads.exam2016.gameApi.GameConfiguration;
import com.woact.dolplads.exam2016.gameApi.dto.AnswerDto;
import com.woact.dolplads.exam2016.gameApi.dto.GameDto;
import com.woact.dolplads.exam2016.gameApi.dto.ResultDto;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.Is.is;

import io.restassured.RestAssured;
import io.restassured.http.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by dolplads on 01/12/2016.
 */
public class GameResourceTestNot extends GameApplicationTestBase {
    @ClassRule
    public static final DropwizardAppRule<GameConfiguration> RULE =
            new DropwizardAppRule<>(GameApplication.class, ResourceHelpers.resourceFilePath("game.yml"));
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(Integer.parseInt(RULE.getConfiguration().getTestUrl()));
}