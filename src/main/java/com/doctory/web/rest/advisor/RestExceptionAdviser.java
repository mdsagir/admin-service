package com.doctory.web.rest.advisor;

import com.doctory.common.DataNotFoundException;
import com.doctory.common.SomethingWentWrong;
import com.doctory.domain.ResponseModel;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestExceptionAdviser {

    @ExceptionHandler({DataNotFoundException.class})
    public ResponseEntity<ResponseModel> handleDataNotFoundException(DataNotFoundException dataNotFoundException) {
        ResponseModel responseModel = ResponseModel.of(dataNotFoundException.getMessage());
        return new ResponseEntity<>(responseModel, NOT_FOUND);
    }

    @ExceptionHandler(SomethingWentWrong.class)
    public ResponseEntity<ResponseModel> handleSomethingWentWring(SomethingWentWrong somethingWentWrong) {
        ResponseModel responseModel = ResponseModel.of(somethingWentWrong.getMessage());
        return new ResponseEntity<>(responseModel, INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        var errors = new HashMap<String, String>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
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
