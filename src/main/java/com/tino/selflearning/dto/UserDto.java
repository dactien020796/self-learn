package com.tino.selflearning.dto;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

  private Long id;
  private String fullName;
  private String username;
  private String password;
  private Set<RoleDto> roles;

}
