package no.exam.dolplads.gameCommands.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dolplads on 12/12/2016.
 */
@ApiModel("An answer")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AnswerDto {
    @ApiModelProperty("id of the quiz thats beeing answered")
    @XmlElement
    @JsonProperty
    public Long quizId;

    @ApiModelProperty("index of the correct answer")
    @XmlElement
    @JsonProperty
    public int correctAnswerIndex = -1;
}
