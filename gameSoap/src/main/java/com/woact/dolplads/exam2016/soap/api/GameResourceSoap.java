package com.woact.dolplads.exam2016.soap.api;

import no.exam.dolplads.gameCommands.dto.AnswerDto;
import no.exam.dolplads.gameCommands.dto.GameDto;
import no.exam.dolplads.gameCommands.dto.ResultDto;

import javax.jws.WebService;

/**
 * Created by dolplads on 13/12/2016.
 */
@WebService(name = "GameResource")
public interface GameResourceSoap {

    GameDto getRandomGame();

    ResultDto postAnswer(AnswerDto answerDto);
}
