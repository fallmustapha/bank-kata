package com.test.bankkata.api.exceptionHandlers;

import com.test.bankkata.api.dto.responses.ResponseMessage;
import com.test.bankkata.exceptions.AccountNotFoundException;
import com.test.bankkata.exceptions.InvalidOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<ResponseMessage> handleValidationException(MethodArgumentNotValidException exception, WebRequest request){
        ResponseMessage message=new ResponseMessage(exception.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST ,HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return  new ResponseEntity(message,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)

    public ResponseEntity<ResponseMessage> handleAccountNotFoundException(AccountNotFoundException exception, WebRequest request){
        ResponseMessage message=new ResponseMessage(exception.getMessage(),HttpStatus.BAD_REQUEST ,HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return  new ResponseEntity(message,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidOperationException.class)

    public ResponseEntity<ResponseMessage> handleInvalidOperationException(InvalidOperationException exception, WebRequest request){
        ResponseMessage message=new ResponseMessage(exception.getMessage(),HttpStatus.NOT_ACCEPTABLE ,HttpStatus.NOT_ACCEPTABLE.value(), LocalDateTime.now());
        return  new ResponseEntity(message,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)

    public ResponseEntity<ResponseMessage> handleOtherException(Exception exception, WebRequest request){
        ResponseMessage message=new ResponseMessage("Something went wrong please retry later",HttpStatus.INTERNAL_SERVER_ERROR ,HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return  new ResponseEntity(message,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
