package com.woact.dolplads.exam2016.gameApi.client;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.woact.dolplads.exam2016.backend.entity.Category;
import com.woact.dolplads.exam2016.gameApi.db.GameDAO;
import com.woact.dolplads.exam2016.dtos.dto.CategoryDto;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dolplads on 27/11/2016.
 * to be reached at http://localhost:9000/app/api/games/hei
 * swagger at http://localhost:9000/app/
 */
@Api
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/games")
public class GameResource {
    private final UriBuilder base = UriBuilder.fromUri("http://localhost:8080/quiz/api/categories");
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

    @POST
    public Response createCategory(CategoryDto dto) {
        Response response = new PostEntity(dto).execute();

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

    private class PostEntity extends HystrixCommand<Response> {
        private CategoryDto toPost;

        PostEntity(CategoryDto toPost) {
            super(HystrixCommandGroupKey.Factory.asKey("Interactions with quizapi"));
            this.toPost = toPost;
        }

        @Override
        protected Response run() throws Exception {
            return client.target(base.build())
                    .request()
                    .post(Entity.entity(toPost, MediaType.APPLICATION_JSON));
        }

        @Override
        protected Response getFallback() {
            System.out.println("had to fall back");
            return Response.serverError().build();
        }

    }
}
