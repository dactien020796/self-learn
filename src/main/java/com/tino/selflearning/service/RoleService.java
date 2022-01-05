package com.tino.selflearning.service;

import com.tino.selflearning.dto.RoleDto;
import com.tino.selflearning.entity.Role;
import com.tino.selflearning.entity.User;
import com.tino.selflearning.exception.RoleException;
import com.tino.selflearning.mapper.RoleMapper;
import com.tino.selflearning.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

  private final RoleRepository repository;
  private final RoleMapper mapper;
  private final UserService userService;

  public RoleDto save(RoleDto role) {
    if (repository.findByName(role.getName()).isPresent()) {
      throw RoleException.roleAlreadyExist().with("name", role.getName());
    }
    com.tino.selflearning.entity.Role entity = mapper.mapToEntity(role);
    return repository.save(entity, mapper::mapToDto);
  }

  public Role getRoleByName(String roleName) {
    return repository.findByName(roleName).orElseThrow(() -> {
      throw RoleException.roleNotFound().with("name", roleName);
    });
  }

  public void addRoleToUser(String username, String roleName) {
    User user = userService.loadUserByUsername(username);
    Role role = this.getRoleByName(roleName);
    userService.updateRole(user, role);
  }
}
