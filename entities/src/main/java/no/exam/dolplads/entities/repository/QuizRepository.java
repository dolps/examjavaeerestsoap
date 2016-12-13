package no.exam.dolplads.entities.repository;

import no.exam.dolplads.entities.annotations.Repository;
import no.exam.dolplads.entities.entity.Quiz;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by dolplads on 12/12/2016.
 */
@Repository
@SuppressWarnings("unchecked")
public class QuizRepository extends CrudRepository<Long, Quiz> {
    QuizRepository() {
        super(Quiz.class);
    }

    public List<Quiz> findAll(int offset, int limit, long subCategoryId) {
        Query query;
        if (subCategoryId != -1) {
            query = entityManager.createQuery("select quiz from Quiz quiz where quiz.subCategoryId = :subCategoryId").
                    setParameter("subCategoryId", subCategoryId);
        } else {
            query = entityManager.createQuery("select quiz from Quiz quiz");
        }

        return query.getResultList();
    }
}
