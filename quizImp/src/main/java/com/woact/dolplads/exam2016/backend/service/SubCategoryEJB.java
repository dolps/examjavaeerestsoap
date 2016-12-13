package com.woact.dolplads.exam2016.backend.service;

import com.woact.dolplads.exam2016.backend.entity.SubCategory;
import com.woact.dolplads.exam2016.backend.repository.SubCategoryRepository;
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
@Setter
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
