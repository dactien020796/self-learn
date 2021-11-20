package com.tino.selflearning.config;

import com.tino.selflearning.filter.JwtTokenFilter;
import com.tino.selflearning.service.UserService;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true, // enables @Secured annotation
    jsr250Enabled = true,  // enables @RolesAllowed annotation
    prePostEnabled = true  // enables @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter annotations
)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserService userService;
  private final JwtTokenFilter jwtTokenFilter;

  /**
   * Use Bcrypt for password encoder.
   */
  @Bean("BCryptPasswordEncoder")
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService)
        .passwordEncoder(passwordEncoder());
    super.configure(auth);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http = http.cors().and().csrf().disable();

    // Set session management to stateless
    http = http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and();

    // Exception handling for filter
    http.exceptionHandling()
        .accessDeniedHandler((request, response, accessDeniedException)
            -> response.sendError(HttpServletResponse.SC_FORBIDDEN))
        .authenticationEntryPoint((request, response, authException)
            -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED));

    // Set permissions on endpoints
    http.authorizeRequests()
        // Our public endpoints
        .antMatchers("/api/public/**").permitAll()
        .antMatchers(HttpMethod.POST, "/api/login").permitAll()
        .antMatchers(HttpMethod.POST, "/api/users/signup").permitAll()
        // Our private endpoints
        .anyRequest().authenticated();

    // Add JWT token filter
    http.addFilterBefore(
        jwtTokenFilter,
        UsernamePasswordAuthenticationFilter.class
    );
  }
}
