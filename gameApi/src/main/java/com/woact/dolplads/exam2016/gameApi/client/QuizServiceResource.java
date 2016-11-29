package com.woact.dolplads.exam2016.gameApi.client;

import com.woact.dolplads.exam2016.gameApi.db.GameDAO;


import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

/**
 * Created by dolplads on 27/11/2016.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/games")
public class QuizServiceResource {
    private Client client;
    private GameDAO gameRepository;
    private String quizServerUrl;

    @GET
    public String hello() {
        return "hello";
    }

    public QuizServiceResource(Client client, GameDAO gameRepository) {
        this.client = client;
        this.gameRepository = gameRepository;
        this.quizServerUrl = "http://localhost:8080/quiz/api";
    }
}
