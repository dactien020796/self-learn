package com.tino.selflearning.exception;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ServiceException extends RuntimeException {

  private final Map<String, Object> data;
  private final ErrorCode errorCode;
  private final HttpStatus status;
  private final String codeNumber;

  public ServiceException with(String key, Object value) {
    this.data.put(key, value);
    return this;
  }
}
