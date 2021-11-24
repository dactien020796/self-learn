package com.tino.selflearning.exception;

import java.nio.file.AccessDeniedException;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({Exception.class, RuntimeException.class})
  public ResponseEntity<ExceptionResponse> handleOthersException(Exception ex) {
    logger.error(ex.getMessage(), ex);
    ExceptionResponse dto = new ExceptionResponse.ExceptionResponseBuilder()
        .message(ex.getCause() == null ? ex.getLocalizedMessage() : ex.getCause().getMessage())
        .stackTrace(ExceptionUtils.getStackTrace(ex))
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .build();
    return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({EntityNotFoundException.class, UsernameNotFoundException.class})
  public ResponseEntity<ExceptionResponse> handleConflict(Exception ex) {
    logger.error(ex.getMessage(), ex);
    ExceptionResponse dto = new ExceptionResponse.ExceptionResponseBuilder()
        .message(ex.getCause() == null ? ex.getLocalizedMessage() : ex.getCause().getMessage())
        .stackTrace(ExceptionUtils.getStackTrace(ex))
        .status(HttpStatus.NOT_FOUND.value())
        .build();
    return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<ExceptionResponse> handleAccessDenied(Exception ex) {
    logger.error(ex.getMessage(), ex);
    ExceptionResponse dto = new ExceptionResponse.ExceptionResponseBuilder()
            .message(ex.getCause() == null ? ex.getLocalizedMessage() : ex.getCause().getMessage())
            .stackTrace(ExceptionUtils.getStackTrace(ex))
            .status(HttpStatus.FORBIDDEN.value())
            .build();
    return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
  }
}
