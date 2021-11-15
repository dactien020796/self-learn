package com.tino.selflearning.exception;

import javax.persistence.EntityNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ExceptionResponse> handleOthersException(RuntimeException ex, WebRequest request) {
    ExceptionResponse dto = new ExceptionResponse.ExceptionResponseBuilder()
        .message(ex.getCause() == null ? ex.getLocalizedMessage() : ex.getCause().getMessage())
        .stackTrace(ExceptionUtils.getStackTrace(ex))
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .build();
    return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({EntityNotFoundException.class})
  public ResponseEntity<ExceptionResponse> handleConflict(RuntimeException ex, WebRequest request) {
    ExceptionResponse dto = new ExceptionResponse.ExceptionResponseBuilder()
        .message(ex.getCause() == null ? ex.getLocalizedMessage() : ex.getCause().getMessage())
        .stackTrace(ExceptionUtils.getStackTrace(ex))
        .status(HttpStatus.NOT_FOUND.value())
        .build();
    return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
  }
}
