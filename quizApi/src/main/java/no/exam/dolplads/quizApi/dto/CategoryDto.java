package no.exam.dolplads.quizApi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by dolplads on 30/11/2016.
 */
@ApiModel("A category")
public class CategoryDto {
    @ApiModelProperty("unique id for category")
    public Long id;

    @ApiModelProperty("the category name")
    public String name;

    public List<SubCategoryDTO> subCategoryDTOList;


}
