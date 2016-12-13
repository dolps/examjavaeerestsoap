package no.exam.dolplads.quizImpl.transformers;

import no.exam.dolplads.entities.entity.Category;
import no.exam.dolplads.entities.entity.SubCategory;
import no.exam.dolplads.quizApi.dto.CategoryDto;
import no.exam.dolplads.quizApi.dto.SubCategoryDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dolplads on 30/11/2016.
 */
public class CategoryConverter {
    private CategoryConverter() {
    }

    public static CategoryDto transform(Category category, boolean expanded) {
        Objects.requireNonNull(category);

        CategoryDto dto = new CategoryDto();
        dto.id = category.getId();
        dto.name = category.getName();
        if (expanded) {
            dto.subCategoryDTOList = transformAllSubs(category.getSubCategories());
        } else {
            dto.subCategoryDTOList = new ArrayList<>();
        }

        return dto;
    }

    public static List<CategoryDto> transform(List<Category> categories, boolean expanded) {
        Objects.requireNonNull(categories);
        List<CategoryDto> dtos;

        dtos = categories.stream().map(cat -> transform(cat, expanded))
                .collect(Collectors.toList());

        return dtos;
        //return categories.stream().map(category -> transform(category));
    }

    public static Category transform(CategoryDto categoryDto) {
        Objects.requireNonNull(categoryDto);
        Category category = new Category();
        category.setName(categoryDto.name);
        category.setId(categoryDto.id);

        return category;
    }

    // todo add fields
    public static SubCategory transformSub(SubCategoryDTO category) {
        Objects.requireNonNull(category);
        SubCategory subCategory = new SubCategory();
        subCategory.setName(category.name);
        subCategory.setId(category.id);
        subCategory.setParentCategory(transform(category.parentCategory));

        return subCategory;
    }

    public static List<SubCategory> transformSub(List<SubCategoryDTO> categories) {
        Objects.requireNonNull(categories);
        return categories.stream().map(CategoryConverter::transformSub).collect(Collectors.toList());
    }

    public static List<SubCategoryDTO> transformAllSubs(List<SubCategory> subCategories) {
        Objects.requireNonNull(subCategories);
        return subCategories.stream().map(CategoryConverter::transformSub).collect(Collectors.toList());
    }

    // TODO: 12/12/2016 add false declaration to top
    public static SubCategoryDTO transformSub(SubCategory subCategory) {
        Objects.requireNonNull(subCategory);
        SubCategoryDTO dto = new SubCategoryDTO();
        dto.id = subCategory.getId();
        dto.name = subCategory.getName();
        dto.parentCategory = transform(subCategory.getParentCategory(), false);

        return dto;
    }

}
