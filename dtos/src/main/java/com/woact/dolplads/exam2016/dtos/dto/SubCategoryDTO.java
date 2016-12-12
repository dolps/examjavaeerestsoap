package com.woact.dolplads.exam2016.dtos.dto;

import com.woact.dolplads.exam2016.backend.annotations.NotEmpty;
import com.woact.dolplads.exam2016.backend.enums.CategoryEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dolplads on 05/12/2016.
 */
@ApiModel("a subcategory")
public class SubCategoryDTO {
    @ApiModelProperty(value = "unique id for subcategory", readOnly = true)
    public Long id;

    @ApiModelProperty(value = "the parent category", required = true)
    public CategoryDto parentCategory;
    public String name;
}
