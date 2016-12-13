package no.exam.dolplads.gameCommands.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dolplads on 12/12/2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AnswerDto {
    @XmlElement
    @JsonProperty
    public Long quizId;
    @XmlElement
    @JsonProperty
    public int correctAnswerIndex = -1;
}
