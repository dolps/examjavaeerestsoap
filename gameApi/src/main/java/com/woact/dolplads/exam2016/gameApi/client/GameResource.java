package com.woact.dolplads.exam2016.gameApi.client;

import com.woact.dolplads.exam2016.gameApi.db.GameDAO;
import dto.CategoryDto;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by dolplads on 27/11/2016.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/games")
public class GameResource {
    private Client client;
    private GameDAO gameRepository;
    private final String quizServerUrl = "http://localhost:8080/quiz/api";

    @GET
    @Path("/hei")
    public String hello() {
        return "hello";
    }

    public GameResource(Client client, GameDAO gameRepository) {
        this.client = client;
        this.gameRepository = gameRepository;
    }

    @GET
    public Long createCategory() {
        WebTarget webTarget = client.target(quizServerUrl + "/categories");
        CategoryDto dto = new CategoryDto();
        dto.text = "hello";
        webTarget.request()
                .post(Entity.entity(dto, MediaType.APPLICATION_JSON));

        return 1L;
    }
}
