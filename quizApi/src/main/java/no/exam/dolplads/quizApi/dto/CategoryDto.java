package no.exam.dolplads.quizApi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by dolplads on 30/11/2016.
 */
@ApiModel("A category")
public class CategoryDto {
    @ApiModelProperty(value = "unique id for categorys", readOnly = true)
    public Long id;

    @ApiModelProperty("the category name")
    public String name;

    @ApiModelProperty("list of subcategories")
    public List<SubCategoryDTO> subCategoryDTOList;
}
