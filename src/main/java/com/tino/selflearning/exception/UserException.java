package com.tino.selflearning.exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class UserException extends ServiceException {

  private static final String USER_NOT_FOUND = "001";
  private static final String USER_EXISTED = "002";

  public UserException(String codeNumber, HttpStatus httpStatus) {
    super(new HashMap<>(), ErrorCode.USER, httpStatus, codeNumber);
  }

  public static UserException userNotFound() {
    return new UserException(UserException.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
  }

  public static UserException userAlreadyExist() {
    return new UserException(UserException.USER_EXISTED, HttpStatus.BAD_REQUEST);
  }
}
