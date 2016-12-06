package com.woact.dolplads.exam2016.quizApi.rest.transformers;

import com.woact.dolplads.exam2016.backend.entity.Category;
import com.woact.dolplads.exam2016.backend.entity.SubCategory;
import com.woact.dolplads.exam2016.dtos.dto.CategoryDto;
import com.woact.dolplads.exam2016.dtos.dto.SubCategoryDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dolplads on 30/11/2016.
 */
public class CategoryConverter {
    private CategoryConverter() {
    }

    public static CategoryDto transform(Category category) {
        Objects.requireNonNull(category);

        CategoryDto dto = new CategoryDto();
        dto.id = category.getId();
        dto.text = category.getText();

        return dto;
    }

    public static List<CategoryDto> transform(List<Category> categories) {
        Objects.requireNonNull(categories);
        return categories.stream().map(CategoryConverter::transform).collect(Collectors.toList());
    }

    public static Category transform(CategoryDto categoryDto) {
        Objects.requireNonNull(categoryDto);
        Category category = new Category();
        category.setText(categoryDto.text);
        category.setId(categoryDto.id);

        return category;
    }

    public static SubCategory transformSub(SubCategoryDTO category) {
        Objects.requireNonNull(category);
        SubCategory subCategory = new SubCategory();
        subCategory.setCategoryEnum(category.categoryText);
        Category parent = new Category();
        parent.setId(category.parentCategory.id);
        subCategory.setParentCategory(parent);

        return subCategory;
    }

    public static List<SubCategory> transformSub(List<SubCategoryDTO> categories) {
        Objects.requireNonNull(categories);
        return categories.stream().map(CategoryConverter::transformSub).collect(Collectors.toList());
    }

}
