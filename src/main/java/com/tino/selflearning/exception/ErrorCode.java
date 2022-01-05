package com.tino.selflearning.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  COMMON("COM"),
  USER("USR"),
  ROLE("ROL"),
  PRODUCT("PRO");

  private final String code;
}
