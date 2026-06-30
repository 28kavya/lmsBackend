package com.learnhub.service;

import com.learnhub.dto.LoginRequestDTO;
import com.learnhub.dto.RegisterRequestDTO;
import com.learnhub.dto.UserDTO;
import com.learnhub.dto.mapper.LoginRequestDTOMapper;
import com.learnhub.dto.mapper.RegisterRequestDTOMapper;
import com.learnhub.entity.User;
import com.learnhub.exception.UserNotFoundException;
import com.learnhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
//
    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisterRequestDTO register(User request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Directly storing password
        user.setRole(request.getRole());

        user =userRepository.save(user);
        return RegisterRequestDTOMapper.mapToRegisterRequestDTO(user);
    }

    public LoginRequestDTO login(User request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        return LoginRequestDTOMapper.mapToLoginRequestDTO(user);
    }
}
