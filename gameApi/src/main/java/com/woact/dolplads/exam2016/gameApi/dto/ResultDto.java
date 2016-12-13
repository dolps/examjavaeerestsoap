package com.woact.dolplads.exam2016.gameApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by dolplads on 12/12/2016.
 */
public class ResultDto {
    @JsonProperty
    public boolean correct;

    public ResultDto(boolean correct) {
        this.correct = correct;
    }

    public ResultDto() {
    }
}
