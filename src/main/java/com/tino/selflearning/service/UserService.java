package com.tino.selflearning.service;

import com.tino.selflearning.dto.UserDto;
import com.tino.selflearning.entity.Role;
import com.tino.selflearning.entity.User;
import com.tino.selflearning.mapper.UserMapper;
import com.tino.selflearning.repository.UserRepository;
import com.tino.selflearning.utils.JwtTokenUtil;
import java.util.List;
import javax.persistence.EntityExistsException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final UserMapper mapper;
  private final PasswordEncoder passwordEncoder;
  private final CachingService cachingService;
  private final JwtTokenUtil jwtTokenUtil;

  public UserService(UserRepository userRepository, UserMapper mapper,
                    @Lazy PasswordEncoder passwordEncoder, CachingService cachingService, JwtTokenUtil jwtTokenUtil) {
    this.userRepository = userRepository;
    this.mapper = mapper;
    this.passwordEncoder = passwordEncoder;
    this.cachingService = cachingService;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @Override
  public User loadUserByUsername(String name) throws UsernameNotFoundException {
    return userRepository.findByUsername(name).orElseThrow(() -> {
      throw new UsernameNotFoundException(String.format("User: %s not found", name));
    });
  }

  public List<UserDto> getUsers(Pageable page) {
    return userRepository.findAll(page, mapper::mapToDto);
  }

  public UserDto register(UserDto userDto) {
    if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
      throw new EntityExistsException(String.format("User with username %s already exist", userDto.getUsername()));
    }
    User user = mapper.mapToEntity(userDto);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user, mapper::mapToDto);
  }

  public void updateRole(User user, Role role) {
    user.getRoles().add(role);
    userRepository.save(user);
  }

  public void logout(String jwt) {
    String username = jwtTokenUtil.getClaim(jwt, "username");;
    cachingService.blacklistJwt(username, jwt);
  }
}
