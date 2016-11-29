package com.woact.dolplads.exam2016.gameApi.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dolplads on 29/11/2016.
 */
@Entity
public class Game {
    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty
    private String zapp;

    @JsonProperty
    @ElementCollection
    List<String> quizzes = new ArrayList<>();

    public Game() {
    }

    public Game(String zapp) {
        this.zapp = zapp;
    }

    public String getZapp() {
        return zapp;
    }

    public void setZapp(String zapp) {
        this.zapp = zapp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
