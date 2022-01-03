package com.tino.selflearning.filter;

import com.tino.selflearning.cache.CacheService;
import com.tino.selflearning.entity.User;
import com.tino.selflearning.service.UserService;
import com.tino.selflearning.utils.JwtTokenUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

  private final UserService userService;
  private final JwtTokenUtil jwtTokenUtil;
  private final CacheService cachingService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {

    // Get authorization header and validate
    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
      chain.doFilter(request, response);
      return;
    }

    // Get jwt token and validate
    final String token = header.split(" ")[1].trim();
    if (!jwtTokenUtil.validate(token)) {
      chain.doFilter(request, response);
      return;
    }

    // Check if jwt is in blacklist or not
    String username = jwtTokenUtil.getClaim(token, "username");
    if (cachingService.isTokenBlackedList(username, token)) {
      chain.doFilter(request, response);
      return;
    }

    // Get user identity and set it on the spring security context
    if (StringUtils.isNoneBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
      // TODO: the database hit is optional. You could also encode the user’s username and roles inside JWT claims
      //  and create the UserDetails object by parsing those claims from the JWT. That would avoid the database hit.
      //
      //  However, Loading the current details of the user from the database might still be helpful.
      //  For example, you might wanna disallow login with this JWT if the user’s role has changed, or the user has updated his password after the creation of this JWT.
      User userDetails = userService.loadUserByUsername(username);

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails == null ? List.of() : userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      logger.info("authenticated user " + username + ", setting security context");
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    chain.doFilter(request, response);
  }
}
