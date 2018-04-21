package au.com.maxcheung.futureclearer.future;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(HIGHEST_PRECEDENCE)
class LookupControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(LookupControllerAdvice.class);

    @Autowired
    LookupControllerAdvice() {
    }

    @ExceptionHandler({ FileLoadException.class })
    @ResponseBody
    ResponseEntity<String> handleFileLoadException(Exception exception) {
        LOGGER.info("FileLoadException being handled: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

}
