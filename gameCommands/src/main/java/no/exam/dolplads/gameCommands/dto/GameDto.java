package no.exam.dolplads.gameCommands.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by dolplads on 12/12/2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GameDto {
    @XmlElement
    @JsonProperty
    public Long id;
    @XmlElement
    @JsonProperty
    public String question;
    @XmlElement
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
