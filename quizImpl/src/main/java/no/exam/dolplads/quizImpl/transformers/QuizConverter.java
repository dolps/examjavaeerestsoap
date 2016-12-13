package no.exam.dolplads.quizImpl.transformers;

import no.exam.dolplads.entities.entity.Quiz;
import no.exam.dolplads.quizApi.dto.ListDto;
import no.exam.dolplads.quizApi.dto.QuizDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dolplads on 12/12/2016.
 */
public class QuizConverter {
    public static Quiz transform(QuizDto quizDto) {
        Quiz quiz = new Quiz();

        quiz.setQuestion(quizDto.question);
        quiz.setAnswers(quizDto.answers);
        quiz.setCorrectIndex(quizDto.correctIndex);
        quiz.setSubCategoryId(quizDto.subCategoryId);

        return quiz;
    }

    public static List<QuizDto> transform(List<Quiz> quizList) {
        Objects.requireNonNull(quizList);
        List<QuizDto> dtos;

        dtos = quizList.stream().map(QuizConverter::transform)
                .collect(Collectors.toList());

        return dtos;
    }

    public static QuizDto transform(Quiz quiz) {
        Objects.requireNonNull(quiz);
        QuizDto dto = new QuizDto();

        dto.id = quiz.getId();
        dto.subCategoryId = quiz.getSubCategoryId();
        dto.answers = quiz.getAnswers();
        dto.correctIndex = quiz.getCorrectIndex();
        dto.question = quiz.getQuestion();

        return dto;
    }

    public static ListDto<QuizDto> transform(List<Quiz> quizList, int offset, int limit) {
        Objects.requireNonNull(quizList);

        List<QuizDto> quizDtos = null;
        quizDtos = quizList.stream().skip(offset)
                .limit(limit)
                .map(QuizConverter::transform)
                .collect(Collectors.toList());

        ListDto<QuizDto> dto = new ListDto<>();
        dto.list = quizDtos;
        dto._links = new ListDto.ListLinks();
        dto.rangeMin = offset;
        dto.rangeMax = dto.rangeMin + quizDtos.size() - 1;
        dto.totalSize = quizList.size();

        return dto;
    }

}
