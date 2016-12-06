package com.woact.dolplads.exam2016.backend.repository;

import com.woact.dolplads.exam2016.backend.annotations.Repository;
import com.woact.dolplads.exam2016.backend.entity.SubCategory;

/**
 * Created by dolplads on 05/12/2016.
 */
@Repository
public class SubCategoryRepository extends CrudRepository<Long, SubCategory> {
    SubCategoryRepository() {
        super(SubCategory.class);
    }
}
