package com.learnhub.dto.mapper;

import com.learnhub.dto.RegisterRequestDTO;
import com.learnhub.entity.User;

import java.util.List;

public class RegisterRequestDTOMapper {
    public static RegisterRequestDTO mapToRegisterRequestDTO(User user) {
         return RegisterRequestDTO.builder()
                 .name(user.getName())
                 .email(user.getEmail())
                 .role(String.valueOf(user.getRole()))
                 .build();

    }
}
