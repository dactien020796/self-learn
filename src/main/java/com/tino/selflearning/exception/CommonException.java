package com.tino.selflearning.exception;

import java.util.HashMap;
import org.springframework.http.HttpStatus;

public class CommonException extends ServiceException {

  private static final String ENTITY_NOT_FOUND = "001";

  private CommonException(String codeNumber, HttpStatus status) {
    super(new HashMap<>(), ErrorCode.COMMON, status, codeNumber);
  }

  public static CommonException entityNotFound() {
    return new CommonException(ENTITY_NOT_FOUND, HttpStatus.NOT_FOUND);
  }
}
