package com.woact.dolplads.exam2016.backend.entity;

import com.woact.dolplads.exam2016.backend.enums.CategoryEnum;
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
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private CategoryEnum categoryEnum;

    @ManyToOne
    @JoinColumn
    private Category parentCategory;

    public SubCategory(CategoryEnum categoryEnum) {
        this.categoryEnum = categoryEnum;
    }
}
