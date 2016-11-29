package com.woact.dolplads.exam2016.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by dolplads on 29/11/2016.
 */
@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String text;

    public Category(String text) {
        this.text = text;
    }

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
