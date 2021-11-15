package com.tino.selflearning.service;

import com.tino.selflearning.dto.UserDto;
import com.tino.selflearning.entity.User;
import com.tino.selflearning.mapper.UserMapper;
import com.tino.selflearning.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final UserMapper mapper;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, UserMapper mapper, @Lazy PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.mapper = mapper;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User loadUserByUsername(String name) throws UsernameNotFoundException {
    return userRepository.findByUsername(name).orElseThrow(() -> {
      throw new UsernameNotFoundException(String.format("User: %s not found", name));
    });
  }

  public UserDto create(UserDto userDto) {
    User user = mapper.mapToEntity(userDto);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user, mapper::mapToDto);
  }
}
