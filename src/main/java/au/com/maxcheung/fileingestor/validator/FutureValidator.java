package au.com.maxcheung.fileingestor.validator;

import java.util.List;

public interface FutureValidator {

    <T> void validate(List<T> rows);

}
