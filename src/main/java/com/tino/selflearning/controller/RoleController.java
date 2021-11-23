package com.tino.selflearning.controller;

import com.tino.selflearning.dto.AddRoleToUserRequest;
import com.tino.selflearning.dto.RoleDto;
import com.tino.selflearning.exception.RecordNotFoundException;
import com.tino.selflearning.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleController {

  private final RoleService roleService;

  @PostMapping
  public RoleDto add(@RequestBody RoleDto role) {
    return roleService.save(role);
  }

  @PostMapping("/addToUser")
  public void addRoleToUser(@RequestBody AddRoleToUserRequest request)
      throws RecordNotFoundException {
    roleService.addRoleToUser(request.getUsername(), request.getRole());
  }
}
