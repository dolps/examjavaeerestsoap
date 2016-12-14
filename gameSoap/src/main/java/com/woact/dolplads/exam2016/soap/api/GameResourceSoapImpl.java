package com.woact.dolplads.exam2016.soap.api;

import com.google.gson.Gson;
import no.exam.dolplads.gameCommands.commands.GetQuizCommand;
import no.exam.dolplads.gameCommands.dto.AnswerDto;
import no.exam.dolplads.gameCommands.dto.GameDto;
import no.exam.dolplads.gameCommands.dto.ResultDto;
import no.exam.dolplads.quizApi.dto.QuizDto;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.jws.WebService;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 * Created by dolplads on 13/12/2016.
 */
@WebService(endpointInterface = "com.woact.dolplads.exam2016.soap.api.GameResourceSoap")
public class GameResourceSoapImpl implements GameResourceSoap {
    private UriBuilder base;

    public GameResourceSoapImpl() {
        this.base = UriBuilder.fromUri("http://localhost:8090/quiz/api/quizzes");
    }


    @Override
    public GameDto getRandomGame() {
        Response quizResponse = new GetQuizCommand("http://localhost:2222/quiz/api/quizzes/random").execute();

        if (quizResponse.getStatus() == 200) {
            QuizDto dto = new Gson().fromJson(quizResponse.getEntity().toString(), QuizDto.class);
            return new GameDto(dto.id, dto.question, dto.answers);
        } else { // configuration of wiremock not allways stable (safeguarding for the sake of the exam here..)
            throw new RuntimeException("resource unavailable"); // should have been a 500...
        }
    }

    @Override
    public ResultDto postAnswer(AnswerDto answerDto) {
        Response quizResponse = new GetQuizCommand("http://localhost:2222/quiz/api/quizzes/" + answerDto.quizId).execute();

        if (quizResponse.getStatus() == 200) {
            QuizDto dto = new Gson().fromJson(quizResponse.getEntity().toString(), QuizDto.class);
            if (dto.correctIndex == answerDto.correctAnswerIndex) {
                return new ResultDto(true);
            } else {
                return new ResultDto(false);
            }
        }
        return new ResultDto(false);
    }
}
