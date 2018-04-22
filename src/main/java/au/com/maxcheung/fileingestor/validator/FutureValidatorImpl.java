package au.com.maxcheung.fileingestor.validator;

import static au.com.maxcheung.fileingestor.utils.LogUtils.futureLogMsg;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author MAX
 *
 */
@Service
public class FutureValidatorImpl implements FutureValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FutureValidator.class);

    private Validator validator;

    public FutureValidatorImpl() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    
    /* (non-Javadoc)
     * @see au.com.maxcheung.fileingestor.validator.FutureValidator#validate(java.util.List)
     */
    @Override
    public <T> void validate(List<T> rows) {
        int lineNo = 1;
        for (T row : rows) {
            Set<ConstraintViolation<T>> violations = validate(row);
            for (ConstraintViolation<T> violation : violations) {
                logger().error(futureLogMsg("line: " + lineNo + " : " + violation.getPropertyPath() + " : "
                        + violation.getInvalidValue() + " : " + violation.getMessage()));
            }

        }
        logger().info(futureLogMsg("Validated rows : {}"), rows.size());
    }

    private <T> Set<ConstraintViolation<T>> validate(T row) {
        Set<ConstraintViolation<T>> violations = validator.validate(row);
        return violations;
    }

   protected Logger logger() {
        return LOGGER;
    }

}
