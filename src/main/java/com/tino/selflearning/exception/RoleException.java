package com.tino.selflearning.exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class RoleException extends ServiceException {

  private static final String ROLE_NOT_FOUND = "001";
  private static final String ROLE_EXISTED = "002";

  public RoleException(String codeNumber, HttpStatus httpStatus) {
    super(new HashMap<>(), ErrorCode.ROLE, httpStatus, codeNumber);
  }

  public static RoleException roleNotFound() {
    return new RoleException(RoleException.ROLE_NOT_FOUND, HttpStatus.NOT_FOUND);
  }

  public static RoleException roleAlreadyExist() {
    return new RoleException(RoleException.ROLE_EXISTED, HttpStatus.BAD_REQUEST);
  }
}
