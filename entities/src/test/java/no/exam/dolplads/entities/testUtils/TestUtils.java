package no.exam.dolplads.entities.testUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dolplads on 12/10/2016.
 * <p>
 * A little util that can be used in tests
 */
public class TestUtils {
    private final static Logger logger = Logger.getLogger(TestUtils.class.getSimpleName());
    private static Validator validator;

    public static int violations(Object entityInstance, String property) {
        Set<ConstraintViolation<Object>> violations = validator.validateProperty(entityInstance, property);
        logViolations(violations);

        return violations.size();
    }

    public static int violations(Object entityInstance) {
        if (validator == null) {
            throw new RuntimeException("validator == null, set validator");
        }
        Set<ConstraintViolation<Object>> violations = validator.validate(entityInstance);
        logViolations(violations);

        return violations.size();
    }

    public static void setValidator(Validator validator) {
        TestUtils.validator = validator;
    }

    private static void logViolations(Set<ConstraintViolation<Object>> violations) {
        violations.forEach(v -> {
            logger.log(Level.INFO, v.toString() + "\n");
        });
    }


}
