package com.tino.selflearning.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.tino.selflearning.entity.User;
import com.tino.selflearning.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository repository;

  @InjectMocks
  private UserService userService;

  @Test
  public void shouldReturnUser() {
    //given
    User user = new User();
    user.setUsername("tienle");

    when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));
    User existingUser = userService.loadUserByUsername("tienle");
    assertEquals(user, existingUser);
  }

  @Test
  public void shouldThrowExceptionWhenNoUser() {
    when(repository.findByUsername(anyString())).thenReturn(Optional.empty());
    assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("helel"));
  }
}