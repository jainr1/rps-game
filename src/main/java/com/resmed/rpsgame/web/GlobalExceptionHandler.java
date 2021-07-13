package com.resmed.rpsgame.web;

import com.resmed.rpsgame.exception.ErrorMessage;
import com.resmed.rpsgame.exception.RpsGameException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ RpsGameException.class })
    public ResponseEntity<ErrorMessage> rpsGameException(RpsGameException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                ex.getErrorCode().httpStatus.value(),
                LocalDateTime.now(),
                ex.getErrorCode().shortMessage,
                ex.getMessage());
        return new ResponseEntity<>(message, ex.getErrorCode().httpStatus);
    }
}
