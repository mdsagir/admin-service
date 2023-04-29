package com.doctory.web.rest.advisor;

import com.doctory.common.DataNotFoundException;
import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.ResponseModel;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static com.doctory.domain.ResponseModel.of;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestExceptionAdviser {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionAdviser.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseModel> handleValidationExceptions(RuntimeException exception) {
        if (log.isInfoEnabled()) {
            log.error("Error while runtime exception {}", exception.toString());
        }
        ResponseModel responseModel = of("Unable to process the request at this time");
        return new ResponseEntity<>(responseModel, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ResponseModel> handleDataNotFoundException(DataNotFoundException dataNotFoundException) {
        ResponseModel responseModel = of(dataNotFoundException.getMessage());
        return new ResponseEntity<>(responseModel, NOT_FOUND);
    }

    @ExceptionHandler(SomethingWentWrong.class)
    public ResponseEntity<ResponseModel> handleSomethingWentWring(SomethingWentWrong somethingWentWrong) {
        ResponseModel responseModel = of(somethingWentWrong.getMessage());
        return new ResponseEntity<>(responseModel, INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }


    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleRequestParameterException(ConstraintViolationException exception) {

        var errors = new HashMap<String, String>();
        exception.getConstraintViolations().forEach(constraintViolation -> {
            var messageTemplate = constraintViolation.getMessageTemplate();
            errors.put("error", messageTemplate);
        });
        return errors;
    }
}
