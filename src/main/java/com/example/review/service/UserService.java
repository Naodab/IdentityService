package com.example.review.service;

import com.example.review.dto.request.UserCreateRequest;
import com.example.review.dto.request.UserUpdateRequest;
import com.example.review.dto.response.UserResponse;
import com.example.review.entity.User;
import com.example.review.exception.AppException;
import com.example.review.exception.ErrorCode;
import com.example.review.mapper.UserMapper;
import com.example.review.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    public UserResponse create(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())
                || userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getByUsername(String username) {
        return userMapper.toUserResponse(userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public UserResponse getMyInfo() {
        var username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userMapper.toUserResponse(userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @PostAuthorize("returnObject.username==authentication.name")
    public UserResponse update(String username, UserUpdateRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user = userMapper.updateUser(user, request);
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
