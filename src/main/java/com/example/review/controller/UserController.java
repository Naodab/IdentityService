package com.example.review.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.review.dto.request.UserCreateRequest;
import com.example.review.dto.request.UserUpdateRequest;
import com.example.review.dto.response.ApiResponse;
import com.example.review.dto.response.UserResponse;
import com.example.review.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> create(@RequestBody @Valid UserCreateRequest request) {
        log.info("User created: {}", request);
        return ApiResponse.<UserResponse>builder()
                .result(userService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAll() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAll())
                .build();
    }

    @GetMapping("/{username}")
    public ApiResponse<UserResponse> get(@PathVariable String username) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getByUsername(username))
                .build();
    }

    @PutMapping("/{username}")
    public ApiResponse<UserResponse> update(
            @PathVariable String username, @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.update(username, request))
                .build();
    }

    @DeleteMapping("/{username}")
    public ApiResponse<?> delete(@PathVariable String username) {
        userService.deleteByUsername(username);
        return ApiResponse.builder().build();
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> myInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
}
