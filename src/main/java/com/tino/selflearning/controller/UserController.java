package com.tino.selflearning.controller;

import com.tino.selflearning.dto.UserDto;
import com.tino.selflearning.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public UserDto create(@RequestBody UserDto userDto) {
    return userService.create(userDto);
  }
}
