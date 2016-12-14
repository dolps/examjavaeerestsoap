package no.exam.dolplads.gameApi.resource;

import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import no.exam.dolplads.gameCommands.commands.GetQuizCommand;
import no.exam.dolplads.quizApi.dto.QuizDto;
import no.exam.dolplads.quizApi.dto.CategoryDto;
import no.exam.dolplads.gameCommands.dto.AnswerDto;
import no.exam.dolplads.gameCommands.dto.GameDto;
import no.exam.dolplads.gameCommands.dto.ResultDto;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dolplads on 27/11/2016.
 * to be reached at http://localhost:9000/game/api/games
 * swagger at http://localhost:9000/game/
 */
@Api(value = "/games", description = "resource for a game of quiz")
@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameResource {
    private final UriBuilder base;
    private Client client;

    public GameResource(Client client, String externalHost) {
        this.client = client;
        this.base = UriBuilder.fromUri(externalHost + "/quiz/api/quizzes");
    }

    @ApiOperation("get a game chosen randomly")
    @GET
    @Path("random")
    public GameDto getRandomGame() {
        Response quizResponse = new GetQuizCommand(client, base.build() + "/random").execute();


        if (quizResponse.getStatus() == 200) {
            QuizDto dto = new Gson().fromJson(quizResponse.getEntity().toString(), QuizDto.class);
            return new GameDto(dto.id, dto.question, dto.answers);
        } else {
            throw new WebApplicationException("quiz server is unavailable", 500);
        }

    }

    @ApiOperation("post an answer to the quiz")
    @POST
    public ResultDto postAnswer(AnswerDto answerDto) {
        Response quizResponse = new GetQuizCommand(client, base.build() + "/" + answerDto.quizId).execute();
        QuizDto dto = new Gson().fromJson(quizResponse.getEntity().toString(), QuizDto.class);

        if (dto.correctIndex == answerDto.correctAnswerIndex) {
            return new ResultDto(true);
        } else {
            return new ResultDto(false);
        }
    }

    @ApiOperation("get list of existing categories")
    @GET
    public List<CategoryDto> getCategories() {
        CategoryDto dtos[] = client.target(base.build()).request().get(CategoryDto[].class);

        return new ArrayList<>(Arrays.asList(dtos));
    }
}
