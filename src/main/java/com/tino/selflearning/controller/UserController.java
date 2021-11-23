package com.tino.selflearning.controller;

import com.tino.selflearning.dto.UserDto;
import com.tino.selflearning.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public List<UserDto> getAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return userService.getUsers(pageable);
  }

  @PostMapping("/signup")
  public UserDto register(@RequestBody UserDto userDto) {
    return userService.register(userDto);
  }
}
