package com.woact.dolplads.exam2016.dtos.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dolplads on 30/11/2016.
 */
@ApiModel("A category")
public class CategoryDto {
    @ApiModelProperty("unique id for category")
    public Long id;

    @ApiModelProperty("the category text")
    public String text;


}
