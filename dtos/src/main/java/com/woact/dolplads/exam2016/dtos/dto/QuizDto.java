package com.woact.dolplads.exam2016.dtos.dto;

import com.woact.dolplads.exam2016.backend.annotations.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by dolplads on 12/12/2016.
 */

public class QuizDto {
    public Long id;
    public Long subCategoryId;
    public String question;
    public List<String> answers;
    public int correctIndex = -1;

    public QuizDto(String question, List<String> answers, int correctIndex) {
        this.question = question;
        this.answers = answers;
        this.correctIndex = correctIndex;
    }

    public QuizDto() {
    }
}

