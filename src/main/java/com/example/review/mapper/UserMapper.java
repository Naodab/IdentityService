package com.example.review.mapper;

import com.example.review.dto.request.UserCreateRequest;
import com.example.review.dto.request.UserUpdateRequest;
import com.example.review.dto.response.UserResponse;
import com.example.review.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(UserCreateRequest request) {
        return User.builder()
                .username(request.getUsername())
                .dob(request.getDob())
                .email(request.getEmail())
                .lastname(request.getLastname())
                .firstname(request.getFirstname())
                .build();
    }

    public User updateUser(User user, UserUpdateRequest request) {
        return User.builder()
                .active(request.isActive())
                .address(request.getAddress())
                .dob(request.getDob())
                .email(request.getEmail())
                .id(user.getId())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .phone(request.getPhone())
                .username(user.getUsername())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .dob(user.getDob())
                .address(user.getAddress())
                .phone(user.getPhone())
                .lastname(user.getLastname())
                .firstname(user.getFirstname())
                .active(user.isActive())
                .build();
    }
}
