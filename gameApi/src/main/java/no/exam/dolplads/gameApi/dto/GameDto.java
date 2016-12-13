package no.exam.dolplads.gameApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by dolplads on 12/12/2016.
 */
public class GameDto {
    @JsonProperty
    public Long id;
    @JsonProperty
    public String question;
    @JsonProperty
    public List<String> answers;

    public GameDto(Long id, String question, List<String> answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
    }

    public GameDto() {
    }
}
