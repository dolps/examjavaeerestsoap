package dto;

import com.woact.dolplads.exam2016.backend.entity.Category;

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

        return category;
    }
}
