package no.exam.dolplads.quizApi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by dolplads on 12/12/2016.
 */

@ApiModel("A quiz")
public class QuizDto {
    @ApiModelProperty(value = "unique id for quiz", readOnly = true)
    public Long id;
    @ApiModelProperty("id of subcategory")
    public Long subCategoryId;
    @ApiModelProperty("the question")
    public String question;
    @ApiModelProperty("needs to be a total of 4 answers")
    public List<String> answers;
    @ApiModelProperty("correct index is between 0 and 3")
    public int correctIndex = -1;

    public QuizDto(String question, List<String> answers, int correctIndex) {
        this.question = question;
        this.answers = answers;
        this.correctIndex = correctIndex;
    }

    public QuizDto() {
    }
}

