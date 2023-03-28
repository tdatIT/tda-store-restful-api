package com.webapp.tdastore.controller.web;

import com.webapp.tdastore.config.AppVariable;
import com.webapp.tdastore.data.dto.UserDTO;
import com.webapp.tdastore.data.entities.User;
import com.webapp.tdastore.data.entities.VerificationToken;
import com.webapp.tdastore.data.exception.CustomExceptionRuntime;
import com.webapp.tdastore.data.payload.LoginRequest;
import com.webapp.tdastore.data.payload.response.CustomResponse;
import com.webapp.tdastore.data.payload.response.LoginResponse;
import com.webapp.tdastore.event.OnRegistrationCompleteEvent;
import com.webapp.tdastore.security.CustomUserDetails;
import com.webapp.tdastore.security.JwtTokenProvider;
import com.webapp.tdastore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Value("${api.app.jwtExpiration}")
    private Long expiration;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CustomResponse registerAccount(@Valid @RequestBody UserDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomExceptionRuntime(400, bindingResult.getAllErrors().toString());
        }
        if (userService.findByEmail(dto.getEmail()) != null)
            throw new CustomExceptionRuntime(400, "Email has been exsits");
        else {
            User us = userService.registerNewUserAccount(dto);
            applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(us, AppVariable.APP_CONTEXT_URL));
            return new CustomResponse(200,
                    "Resgiter account has been success. Check email to verify account",
                    System.currentTimeMillis());
        }
    }

    @RequestMapping(value = "/verify-account/{token}", method = RequestMethod.GET)
    public CustomResponse verifyAccount(@PathVariable String token) {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        Calendar cal = Calendar.getInstance();
        if (verificationToken == null && verificationToken.isUsed() == true ||
                (verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return new CustomResponse(200, "Token not has expiry or not valid", System.currentTimeMillis());
        }
        User user = verificationToken.getUser();
        user.setStatus(true);
        userService.registerAccount(user);
        return new CustomResponse(200, "Verify account has success", System.currentTimeMillis());
    }
}
