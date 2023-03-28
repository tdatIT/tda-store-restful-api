package com.webapp.tdastore.controller.web;

import com.webapp.tdastore.data.entities.User;
import com.webapp.tdastore.data.exception.CustomExceptionRuntime;
import com.webapp.tdastore.data.payload.LoginRequest;
import com.webapp.tdastore.data.payload.response.LoginResponse;
import com.webapp.tdastore.security.CustomUserDetails;
import com.webapp.tdastore.security.JwtTokenProvider;
import com.webapp.tdastore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Value("${api.app.jwtExpiration}")
    private Long expiration;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponse login(@Valid @RequestBody LoginRequest loginData) {
        User us = userService.findByEmail(loginData.getEmail());
        if (us.isStatus() == false) {
            throw new CustomExceptionRuntime(401, "User hasn't been active");
        }
        try {
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginData.getEmail(),
                    loginData.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //return jwt
            String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
            return new LoginResponse(200, "Login to success",
                    jwt, new Date(new Date().getTime() + expiration));
        } catch (BadCredentialsException exception) {
            throw new CustomExceptionRuntime(401, "Invalid username or password");
        }
    }
}
