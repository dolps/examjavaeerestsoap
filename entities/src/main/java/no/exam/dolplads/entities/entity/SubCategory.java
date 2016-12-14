package no.exam.dolplads.entities.entity;

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
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn
    private Category parentCategory;
}
