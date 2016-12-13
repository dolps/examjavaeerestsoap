package no.exam.dolplads.entities.repository;

import no.exam.dolplads.entities.annotations.Repository;
import no.exam.dolplads.entities.entity.Category;

/**
 * Created by dolplads on 29/11/2016.
 */
@Repository
@SuppressWarnings("unchecked")
public class CategoryRepository extends CrudRepository<Long, Category> {
    CategoryRepository() {
        super(Category.class);
    }
}
