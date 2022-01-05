package com.tino.selflearning.exception;

import com.tino.selflearning.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.Locale;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessageSource messages;

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<ExceptionResponse> handleServiceException(WebRequest request, ServiceException ex) {
    logger.error(ex.getMessage(), ex);
    String message = getInternationalizationMessage(ex, request.getLocale());
    ExceptionResponse dto = new ExceptionResponse.ExceptionResponseBuilder()
        .message(message)
        .stackTrace(ExceptionUtils.getStackTrace(ex))
        .status(ex.getStatus().value())
        .build();
    return new ResponseEntity<>(dto, ex.getStatus());
  }

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

  private String getInternationalizationMessage(ServiceException ex, Locale locale) {
    String message = messages.getMessage("E" + ex.getErrorCode().getCode() + ex.getCodeNumber(), null, locale);
    return StringUtil.replacePlaceholder(message, ex.getData());
  }
}
