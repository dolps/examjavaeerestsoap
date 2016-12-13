package no.exam.dolplads.entities.entity;

import no.exam.dolplads.entities.annotations.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dolplads on 29/11/2016.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    private String name;
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<SubCategory> subCategories;

    public Category(String name) {
        this.name = name;
    }

    public void addSubCategory(SubCategory subCategory) {
        if (subCategories == null) {
            subCategories = new ArrayList<>();
        }
        if (!subCategories.contains(subCategory)) {
            subCategories.add(subCategory);
        }
    }
}
