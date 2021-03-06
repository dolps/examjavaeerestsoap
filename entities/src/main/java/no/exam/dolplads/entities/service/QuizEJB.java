package no.exam.dolplads.entities.service;

import no.exam.dolplads.entities.entity.Quiz;
import no.exam.dolplads.entities.repository.QuizRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Random;

/**
 * Created by dolplads on 12/12/2016.
 */
@Stateless
@Getter
@Setter
@NoArgsConstructor
public class QuizEJB extends CrudEJB<Long, Quiz> {
    private QuizRepository quizRepository;

    @Inject
    public QuizEJB(QuizRepository quizRepository) {
        super(quizRepository);
        this.quizRepository = quizRepository;
    }

    public Quiz findRandom() {
        List<Quiz> quizList = findAll();
        if (quizList.isEmpty()) {
            return null;
        }

        Random random = new Random(System.currentTimeMillis());

        return quizList.get(random.nextInt(quizList.size()));
    }

    public List<Quiz> findAll(int offset, int limit, long subCategoryId) {
        return quizRepository.findAll(offset, limit, subCategoryId);
    }
}
