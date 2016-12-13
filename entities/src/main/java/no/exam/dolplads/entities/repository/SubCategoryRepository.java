package no.exam.dolplads.entities.repository;

import no.exam.dolplads.entities.annotations.Repository;
import no.exam.dolplads.entities.entity.SubCategory;

import java.util.List;

/**
 * Created by dolplads on 05/12/2016.
 */
@Repository
@SuppressWarnings("unchecked")
public class SubCategoryRepository extends CrudRepository<Long, SubCategory> {
    SubCategoryRepository() {
        super(SubCategory.class);
    }

    public List<SubCategory> findAllByParentId(Long id) {
        return super.entityManager
                .createQuery("select subCat from SubCategory subCat where subCat.parentCategory.id = :id")
                .setParameter("id", id)
                .getResultList();
    }
}
