package no.exam.dolplads.gameCommands.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by dolplads on 12/12/2016.
 */
@ApiModel("A game")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GameDto {
    @ApiModelProperty("id of the game")
    @XmlElement
    @JsonProperty
    public Long id;
    @ApiModelProperty("the question of the game")
    @XmlElement
    @JsonProperty
    public String question;
    @ApiModelProperty("list of possible answers")
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
