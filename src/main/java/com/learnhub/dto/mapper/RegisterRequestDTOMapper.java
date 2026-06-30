package com.learnhub.dto.mapper;

import com.learnhub.dto.RegisterRequestDTO;
import com.learnhub.entity.User;

public class RegisterRequestDTOMapper {
    public static RegisterRequestDTO mapToRegisterRequestDTO(User user) {
         return RegisterRequestDTO.builder()
                 .name(user.getName())
                 .email(user.getEmail())
                 .role(user.getRole())
                 .build();

    }
}
