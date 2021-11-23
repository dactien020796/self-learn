package com.tino.selflearning.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExceptionResponse {
  private String message;
  private String stackTrace;
  private int status;
}
