package no.exam.dolplads.gameApi.resource;

import com.google.gson.Gson;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import no.exam.dolplads.quizApi.dto.QuizDto;
import no.exam.dolplads.quizApi.dto.CategoryDto;
import no.exam.dolplads.gameApi.dto.AnswerDto;
import no.exam.dolplads.gameApi.dto.GameDto;
import no.exam.dolplads.gameApi.dto.ResultDto;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dolplads on 27/11/2016.
 * to be reached at http://localhost:9000/app/api/games/hei
 * swagger at http://localhost:9000/app/
 */
@Api
@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameResource {
    private final UriBuilder base;
    private Client client;

    public GameResource(Client client,String externalHost) {
        this.client = client;
        this.base = UriBuilder.fromUri(externalHost + "/quiz/api/quizzes");
    }


    @GET
    @Path("random")
    public GameDto getRandomGame() {
        System.out.println("getting: " + base.build() + "/random");
        Response quizResponse = new GetQuiz(base.build() + "/random").execute();


        if (quizResponse.getStatus() == 200) {
            QuizDto dto = new Gson().fromJson(quizResponse.getEntity().toString(), QuizDto.class);
            return new GameDto(dto.id, dto.question, dto.answers);
        } else {
            throw new WebApplicationException("quiz server is unavailable", 500);
        }

    }

    @POST
    public ResultDto postAnswer(AnswerDto answerDto) {
        Response quizResponse = new GetQuiz(base.build() + "/" + answerDto.quizId).execute();
        QuizDto dto = new Gson().fromJson(quizResponse.getEntity().toString(), QuizDto.class);

        if (dto.correctIndex == answerDto.correctAnswerIndex) {
            return new ResultDto(true);
        } else {
            return new ResultDto(false);
        }
    }


    @Produces(MediaType.TEXT_PLAIN)
    @GET
    @Path("hei")
    public String hello() {
        return "helloi";
    }

    /*
    @POST
    public Response createCategory(CategoryDto dto) {
        Response response = new PostEntity(dto).execute();

        return Response.created(response.getLocation()).build();
    }
    */

    @GET
    public List<CategoryDto> getCategories() {
        CategoryDto dtos[] = client.target(base.build()).request().get(CategoryDto[].class);

        return new ArrayList<>(Arrays.asList(dtos));
    }

    // examplequery
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

    private class GetQuiz extends HystrixCommand<Response> {
        private String fetchUrl;

        GetQuiz(String fetchUrl) {
            super(HystrixCommandGroupKey.Factory.asKey("Interactions with quizAPI"));
            this.fetchUrl = fetchUrl;
        }

        @Override
        protected Response run() throws Exception {
            QuizDto quizDto = client.target(fetchUrl).request().get(QuizDto.class);

            return Response.ok(new Gson().toJson(quizDto), MediaType.APPLICATION_JSON).build();
        }

        @Override
        protected Response getFallback() {
            return Response.serverError().build();
        }
    }
}
