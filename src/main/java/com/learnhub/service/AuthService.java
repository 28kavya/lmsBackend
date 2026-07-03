package com.learnhub.service;

import com.learnhub.dto.LoginRequestDTO;
import com.learnhub.dto.RegisterRequestDTO;
import com.learnhub.dto.mapper.LoginRequestDTOMapper;
import com.learnhub.dto.mapper.RegisterRequestDTOMapper;
import com.learnhub.entity.Roles;
import com.learnhub.entity.User;
import com.learnhub.exception.UserNotFoundException;
import com.learnhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
//
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String ADMIN_EMAIL = "admin@learnhub.com";
    private static final String ADMIN_CODE = "LEARNHUB2026";

    public RegisterRequestDTO register(User request) {
        if(userRepository.findByName(request.getName()).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Directly storing password
        if(request.getEmail().equalsIgnoreCase(ADMIN_EMAIL)
                && ADMIN_CODE.equals(request.getAdminCode())){

            user.setRole(Roles.ADMIN);

        }else{

            user.setRole(Roles.STUDENT);

        }
        user =userRepository.save(user);
        return RegisterRequestDTOMapper.mapToRegisterRequestDTO(user);
    }

    public LoginRequestDTO login(User request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        return LoginRequestDTOMapper.mapToLoginRequestDTO(user);
    }

    public RegisterRequestDTO addInstructor(User request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        User instructor = new User();

        instructor.setName(request.getName());
        instructor.setEmail(request.getEmail());
        instructor.setPassword(passwordEncoder.encode(request.getPassword()));

        instructor.setRole(Roles.INSTRUCTOR);

        instructor = userRepository.save(instructor);

        return RegisterRequestDTOMapper.mapToRegisterRequestDTO(instructor);
    }
}
