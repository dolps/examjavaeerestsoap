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
@ApiModel("A result")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultDto {
    @ApiModelProperty("result of an answer")
    @XmlElement
    @JsonProperty
    public boolean correct;

    public ResultDto(boolean correct) {
        this.correct = correct;
    }

    public ResultDto() {
    }
}
