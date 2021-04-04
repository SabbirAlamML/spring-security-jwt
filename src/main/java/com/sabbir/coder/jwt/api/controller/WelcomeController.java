package com.sabbir.coder.jwt.api.controller;

import com.sabbir.coder.jwt.api.entity.AuthRequest;
import com.sabbir.coder.jwt.api.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
  private final JwtUtil jwtUtil;

  private final AuthenticationManager authenticationManager;

  public WelcomeController(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
  }

  @GetMapping("/welcome")
  public WelcomeResponse welcome() {
    return new WelcomeResponse("Welcome to spring security jwt");
  }

  @PostMapping("/authenticate")
  public AuthResponse generateToken(@RequestBody AuthRequest authRequest) throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authRequest.getUsername(), authRequest.getPassword()));
    } catch (Exception e) {
      throw new Exception("Invalid username and password");
    }
    return new AuthResponse(jwtUtil.generateToken(authRequest.getUsername()));
  }

  @Data
  @AllArgsConstructor
  @Getter
  private static class WelcomeResponse {
    private final String welcomeMessage;
  }

  @Data
  @Getter
  @AllArgsConstructor
  private static class AuthResponse {
    private final String authToken;
  }
}
