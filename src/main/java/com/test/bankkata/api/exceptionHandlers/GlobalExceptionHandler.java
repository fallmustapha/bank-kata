package com.test.bankkata.api.exceptionHandlers;

import com.test.bankkata.api.dto.responses.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ValidationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<ResponseMessage> handleValidationException(MethodArgumentNotValidException exception, WebRequest request){
        ResponseMessage message=new ResponseMessage(exception.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST ,HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return  new ResponseEntity(message,HttpStatus.BAD_REQUEST);
    }
}
