package au.com.maxcheung.futureclearer.validate;

import java.util.List;

public interface FutureValidator {

    <T> void validate(List<T> rows);

}
