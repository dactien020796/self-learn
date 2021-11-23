package com.tino.selflearning.controller;

import com.tino.selflearning.dto.LoginRequest;
import com.tino.selflearning.dto.LoginResponse;
import com.tino.selflearning.entity.User;
import com.tino.selflearning.service.UserService;
import com.tino.selflearning.utils.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
@AllArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserService userService;

  @PostMapping("login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    try {
      Authentication authenticate = authenticationManager
        .authenticate(
          new UsernamePasswordAuthenticationToken(
            request.getUsername(), request.getPassword()
          )
        );

      User user = (User) authenticate.getPrincipal();

      LoginResponse response = LoginResponse.builder()
          .username(user.getUsername())
          .fullName(user.getFullName())
          .token(jwtTokenUtil.generateAccessToken(user))
          .build();
      return ResponseEntity.ok().body(response);
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @PostMapping("logout")
  public void logout(@RequestHeader(name="Authorization") String token) {
    String jwt = token.split(" ")[1].trim();
    userService.logout(jwt);
  }
}
