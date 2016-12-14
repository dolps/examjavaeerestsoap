package no.exam.dolplads.quizApi.hal;

import io.swagger.annotations.ApiModelProperty;

public class HalObject {

    @ApiModelProperty("HAL links")
    public HalLinkSet _links;
}
