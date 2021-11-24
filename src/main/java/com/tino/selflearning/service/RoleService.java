package com.tino.selflearning.service;

import com.tino.selflearning.dto.RoleDto;
import com.tino.selflearning.entity.Role;
import com.tino.selflearning.entity.User;
import com.tino.selflearning.mapper.RoleMapper;
import com.tino.selflearning.repository.RoleRepository;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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
      throw new EntityExistsException(String.format("Role with name %s already exist", role.getName()));
    }
    com.tino.selflearning.entity.Role entity = mapper.mapToEntity(role);
    return repository.save(entity, mapper::mapToDto);
  }

  public Role getRoleByName(String roleName) {
    return repository.findByName(roleName).orElseThrow(() -> {
      throw new EntityNotFoundException(String.format("Role with name %s not exist", roleName));
    });
  }

  public void addRoleToUser(String username, String roleName) {
    User user = userService.loadUserByUsername(username);
    Role role = this.getRoleByName(roleName);
    userService.updateRole(user, role);
  }
}
