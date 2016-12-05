package com.woact.dolplads.exam2016.gameApi.client;

import com.woact.dolplads.exam2016.gameApi.db.GameDAO;
import dto.CategoryDto;
import io.swagger.annotations.Api;
import org.junit.experimental.categories.Categories;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dolplads on 27/11/2016.
 */
@Api
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

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response createCategory(CategoryDto dto) {
        Entity<CategoryDto> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

        Response response = client.target(quizServerUrl + "/categories").request().post(entity);
        System.out.println("response: " + response.getHeaderString("location"));

        return Response.created(response.getLocation()).build();
    }

    @GET
    public List<CategoryDto> getCategories() {
        CategoryDto dtos[] = client.target(quizServerUrl + "/categories").request().get(CategoryDto[].class);

        return new ArrayList<>(Arrays.asList(dtos));
    }

    public void query() {
        Response response =
                ClientBuilder.newClient().target("http://www.google.com/book").request(MediaType.APPLICATION_JSON).get();
        String body = response.readEntity(String.class);

        CategoryDto dto = ClientBuilder.newClient().target("link").request().get(CategoryDto.class);
    }
}
