package com.sabbir.coder.jwt.api.controller;

import com.sabbir.coder.jwt.api.entity.AuthRequest;
import com.sabbir.coder.jwt.api.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
  @Autowired private JwtUtil jwtUtil;

  @Autowired private AuthenticationManager authenticationManager;

  @GetMapping("/welcome")
  public WelcomeResponse welcome() {
    return new WelcomeResponse();
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

  private class WelcomeResponse {

    private final String welcomeMessage = "Welcome to spring security jwt";

    public String getWelcomeMessage() {
      return welcomeMessage;
    }
  }

  private class AuthResponse {
    private final String authToken;

    public AuthResponse(String authToken) {
      this.authToken = authToken;
    }

    public String getAuthToken() {
      return authToken;
    }
  }
}
