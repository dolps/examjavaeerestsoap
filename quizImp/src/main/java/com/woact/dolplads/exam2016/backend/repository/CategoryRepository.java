package com.woact.dolplads.exam2016.backend.repository;

import com.woact.dolplads.exam2016.backend.annotations.Repository;
import com.woact.dolplads.exam2016.backend.entity.Category;

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
