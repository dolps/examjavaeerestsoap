package no.exam.dolplads.entities.annotations;

import javax.validation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.annotation.*;

/**
 * Created by dolplads on 02/10/16.
 */
@NotNull
@Size(min = 1)
@Constraint(validatedBy = {NotEmpty.Validator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Documented
public @interface NotEmpty {
    String message() default "{NotEmpty.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<NotEmpty, String> {
        @Override
        public void initialize(NotEmpty constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return value != null && !value.trim().isEmpty();
        }
    }
}

