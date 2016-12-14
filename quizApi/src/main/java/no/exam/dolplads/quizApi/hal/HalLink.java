package no.exam.dolplads.quizApi.hal;

import io.swagger.annotations.ApiModelProperty;

public class HalLink {

    @ApiModelProperty("URL of the link")
    public String href;

    public HalLink(){}

    public HalLink(String href){
        this.href = href;
    }
}
