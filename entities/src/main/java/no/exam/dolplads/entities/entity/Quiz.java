package no.exam.dolplads.entities.entity;

import no.exam.dolplads.entities.annotations.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by dolplads on 12/12/2016.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private Long subCategoryId;
    @NotEmpty
    private String question;
    @ElementCollection(fetch = FetchType.EAGER)
    @Size(min = 4, max = 4)
    private List<String> answers;
    @Min(0)
    @Max(3)
    private int correctIndex = -1;

    public Quiz(Long subCategoryId, String question, List<String> answers, int correctIndex) {
        this.subCategoryId = subCategoryId;
        this.question = question;
        this.answers = answers;
        this.correctIndex = correctIndex;
    }
}
