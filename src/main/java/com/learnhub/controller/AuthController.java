package com.learnhub.controller;

import com.learnhub.dto.LoginResponse;
import com.learnhub.dto.UserDTO;
import com.learnhub.dto.mapper.RegisterRequestDTOMapper;
import com.learnhub.service.JWTFilterService;
import com.learnhub.dto.RegisterRequestDTO;
import com.learnhub.entity.User;
import com.learnhub.repository.UserRepository;
import com.learnhub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
;
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
    public ResponseEntity<?> login(@RequestBody User request) {

        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getEmail(),
                                    request.getPassword()
                            )
                    );

            if (authentication.isAuthenticated()) {

                // Load the full user from the database
                User user = userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                // Generate token using the full user
                String token = jwtFilter.generateToken(user);
                System.out.println(user.getName());

                return ResponseEntity.ok(new LoginResponse(token,user.getRole().name(),user.getName()));
            }

        } catch (BadCredentialsException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Bad Credentials");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/getuser")
    public List<RegisterRequestDTO> getUser(){
        List<User> users=userRepository.findAll();
        return userRepository.findAll()
                .stream()
                .map(RegisterRequestDTOMapper::mapToRegisterRequestDTO)
                .toList();
    }

    @PostMapping("/addInstructor")
    public ResponseEntity<?> addInstructor(@RequestBody User request) {
        return ResponseEntity.ok(authService.addInstructor(request));
    }
}
