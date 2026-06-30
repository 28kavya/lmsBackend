package com.learnhub.controller;

import com.learnhub.dto.UserDTO;
import com.learnhub.service.JWTFilterService;
import com.learnhub.dto.RegisterRequestDTO;
import com.learnhub.entity.User;
import com.learnhub.repository.UserRepository;
import com.learnhub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTFilterService jwtFilter;

    @PostMapping("/register")
    public RegisterRequestDTO AuthController(@RequestBody User request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody User request) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            if(authentication.isAuthenticated()) {
               return  jwtFilter.generateToken(request);
            }
        }
        catch (BadCredentialsException e) {
            return "Bad Credentials";
        }
        return null;
    }

    @GetMapping("/getuser")
    public List<User> getUser(){
        List<User> users=userRepository.findAll();
        return users;
    }

}
