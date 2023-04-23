package com.doctory.web.rest.advisor;

import com.doctory.common.DataNotFoundException;
import com.doctory.domain.ResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class RestExceptionAdviser {

    @ExceptionHandler({DataNotFoundException.class})
    public ResponseEntity<ResponseModel> handleDataNotFoundException(DataNotFoundException dataNotFoundException){
        ResponseModel responseModel = ResponseModel.of(dataNotFoundException.getMessage());
        return new ResponseEntity<>(responseModel, NOT_FOUND);
    }
}
