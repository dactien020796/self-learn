package com.tino.selflearning.exception;

import java.nio.file.AccessDeniedException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({Exception.class, RuntimeException.class})
  public ResponseEntity<ExceptionResponse> handleOthersException(Exception ex, WebRequest request) {
    ExceptionResponse dto = new ExceptionResponse.ExceptionResponseBuilder()
        .message(ex.getCause() == null ? ex.getLocalizedMessage() : ex.getCause().getMessage())
        .stackTrace(ExceptionUtils.getStackTrace(ex))
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .build();
    return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({RecordNotFoundException.class, UsernameNotFoundException.class})
  public ResponseEntity<ExceptionResponse> handleConflict(Exception ex, WebRequest request) {
    ExceptionResponse dto = new ExceptionResponse.ExceptionResponseBuilder()
        .message(ex.getCause() == null ? ex.getLocalizedMessage() : ex.getCause().getMessage())
        .stackTrace(ExceptionUtils.getStackTrace(ex))
        .status(HttpStatus.NOT_FOUND.value())
        .build();
    return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<ExceptionResponse> handleAccessDenied(Exception ex, WebRequest request) {
    ExceptionResponse dto = new ExceptionResponse.ExceptionResponseBuilder()
            .message(ex.getCause() == null ? ex.getLocalizedMessage() : ex.getCause().getMessage())
            .stackTrace(ExceptionUtils.getStackTrace(ex))
            .status(HttpStatus.FORBIDDEN.value())
            .build();
    return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
  }
}
