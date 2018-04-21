package au.com.maxcheung.futureclearer.validate;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            Set<ConstraintViolation<T>> violations = validator.validate(row);
            for (ConstraintViolation<T> violation : violations) {
                LOGGER.error("line: " + lineNo + " : " + violation.getPropertyPath() + " : "
                        + violation.getInvalidValue() + " : " + violation.getMessage());
            }

        }
        LOGGER.info("Validated rows : {}",  rows.size());
    }

}
