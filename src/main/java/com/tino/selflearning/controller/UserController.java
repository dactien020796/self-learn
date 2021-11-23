package com.tino.selflearning.controller;

import com.tino.selflearning.dto.UserDto;
import com.tino.selflearning.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public List<UserDto> getUsers() {
    return userService.getUsers();
  }

  @PostMapping("/signup")
  public UserDto register(@RequestBody UserDto userDto) {
    return userService.register(userDto);
  }
}
