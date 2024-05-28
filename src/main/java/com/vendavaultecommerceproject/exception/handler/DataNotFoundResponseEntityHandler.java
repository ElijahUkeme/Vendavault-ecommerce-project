package com.vendavaultecommerceproject.exception.handler;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.vendavaultecommerceproject.exception.model.ErrorMessage;

@ResponseStatus
@ControllerAdvice
public class DataNotFoundResponseEntityHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorMessage> dataNotFoundException(DataNotFoundException dataNotFoundException, WebRequest webRequest){
        ErrorMessage errorMessage = new ErrorMessage(dataNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorMessage);
    }
}
