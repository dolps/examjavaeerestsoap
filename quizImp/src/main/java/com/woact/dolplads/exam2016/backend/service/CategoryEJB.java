package com.woact.dolplads.exam2016.backend.service;

import com.woact.dolplads.exam2016.backend.entity.Category;
import com.woact.dolplads.exam2016.backend.entity.SubCategory;
import com.woact.dolplads.exam2016.backend.repository.CategoryRepository;
import com.woact.dolplads.exam2016.backend.repository.SubCategoryRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by dolplads on 29/11/2016.
 */
@Stateless
@Getter
@Setter
@NoArgsConstructor
public class CategoryEJB extends CrudEJB<Long, Category> {
    private CategoryRepository categoryRepository;

    @Inject
    public CategoryEJB(CategoryRepository categoryRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllExpanded() {
        List<Category> result = findAll();
        for (Category c : result) {
            Hibernate.initialize(c.getSubCategories());
        }

        return result;
    }

    public Category findByIdExpanded(Long id) {
        Category c = super.findById(id);
        Hibernate.initialize(c.getSubCategories());

        return c;
    }
}
