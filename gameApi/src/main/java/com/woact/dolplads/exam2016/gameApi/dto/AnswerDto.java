package com.woact.dolplads.exam2016.gameApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by dolplads on 12/12/2016.
 */
public class AnswerDto {
    @JsonProperty
    public Long quizId;
    @JsonProperty
    public int correctAnswerIndex = -1;
}
