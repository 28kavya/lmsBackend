package com.learnhub.dto.mapper;

import com.learnhub.dto.LoginRequestDTO;
import com.learnhub.entity.User;

public class LoginRequestDTOMapper {
    public static LoginRequestDTO mapToLoginRequestDTO(User user) {
        return LoginRequestDTO.builder()
                .email(user.getEmail())
                .build();
    }

}
