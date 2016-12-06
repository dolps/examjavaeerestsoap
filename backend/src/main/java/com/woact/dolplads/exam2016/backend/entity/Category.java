package com.woact.dolplads.exam2016.backend.entity;

import com.woact.dolplads.exam2016.backend.annotations.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by dolplads on 29/11/2016.
 */
@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    private String text;

    public Category(String text) {
        this.text = text;
    }

    public Category() {
    }
}
