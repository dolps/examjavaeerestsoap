package no.exam.dolplads.entities.service;

import no.exam.dolplads.entities.entity.SubCategory;
import no.exam.dolplads.entities.repository.SubCategoryRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by dolplads on 05/12/2016.
 */
@Stateless
@Getter
@NoArgsConstructor
public class SubCategoryEJB extends CrudEJB<Long, SubCategory> {
    private SubCategoryRepository subCategoryRepository;

    @Inject
    public SubCategoryEJB(SubCategoryRepository subCategoryRepository) {
        super(subCategoryRepository);
        this.subCategoryRepository = subCategoryRepository;
    }

    public List<SubCategory> findAllByParentId(Long id) {
        return subCategoryRepository.findAllByParentId(id);
    }
}
