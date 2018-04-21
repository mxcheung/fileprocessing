package au.com.maxcheung.futureclearer.validate;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FutureValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FutureValidator.class);

    private ValidatorFactory factory;
    private Validator validator;

    public FutureValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public <T> void validate(List<T> rows) {
        int lineNo = 1;
        for (T row : rows) {
            Set<ConstraintViolation<T>> violations = validate(row);
            for (ConstraintViolation<T> violation : violations) {
                logger().error("line: " + lineNo + " : " + violation.getPropertyPath() + " : "
                        + violation.getInvalidValue() + " : " + violation.getMessage());
            }

        }
        logger().info("Validated rows : {}",  rows.size());
    }

    private <T> Set<ConstraintViolation<T>> validate(T row) {
        Set<ConstraintViolation<T>> violations = validator.validate(row);
        return violations;
    }

    // make a package private method for testing purposes to allow you to inject a mock
    Logger logger() {
        return LOGGER;
    }
}
