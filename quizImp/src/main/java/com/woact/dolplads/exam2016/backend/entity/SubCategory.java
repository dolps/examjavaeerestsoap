package com.woact.dolplads.exam2016.backend.entity;

import com.woact.dolplads.exam2016.backend.enums.CategoryEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by dolplads on 05/12/2016.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn
    private Category parentCategory;

    public SubCategory(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }
}
