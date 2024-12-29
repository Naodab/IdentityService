package com.example.review.service;

import com.example.review.dto.request.UserCreateRequest;
import com.example.review.dto.response.UserResponse;
import com.example.review.entity.User;
import com.example.review.exception.AppException;
import com.example.review.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static  org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    private User user;
    private UserCreateRequest userCreateRequest;
    private UserResponse userResponse;

    @BeforeEach
    void initData() {
        userCreateRequest = UserCreateRequest.builder()
                .username("john123")
                .firstname("John")
                .lastname("Martha")
                .password("12345678")
                .dob(LocalDate.of(2004, 12, 12))
                .email("john@gmail.com")
                .phone("0905601223")
                .address("Mieu Bong")
                .build();

        userResponse = UserResponse.builder()
                .id("cf0600f3538b3")
                .username("john123")
                .firstname("John")
                .lastname("Martha")
                .dob(LocalDate.of(2004, 12, 12))
                .email("john@gmail.com")
                .phone("0905601223")
                .address("Mieu Bong")
                .build();

        user = User.builder()
                .id("cf0600f3538b3")
                .username("john123")
                .firstname("John")
                .lastname("Martha")
                .dob(LocalDate.of(2004, 12, 12))
                .email("john@gmail.com")
                .phone("0905601223")
                .address("Mieu Bong")
                .build();
    }

    @Test
    public void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var response = userService.create(userCreateRequest);

        // THEN
        assertThat(response.getId()).isEqualTo("cf0600f3538b3");
        assertThat(response.getUsername()).isEqualTo("john123");
        assertThat(response.getFirstname()).isEqualTo("John");
        assertThat(response.getLastname()).isEqualTo("Martha");
        assertThat(response.getDob())
                .isEqualTo(LocalDate.of(2004, 12, 12));
        assertThat(response.getEmail()).isEqualTo("john@gmail.com");
        assertThat(response.getPhone()).isEqualTo("0905601223");
        assertThat(response.getAddress()).isEqualTo("Mieu Bong");
    }

    @Test
    public void createUser_userExisted_fail() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        var exception = assertThrows(AppException.class,
                () -> userService.create(userCreateRequest));

        assertThat(exception.getErrorCode().getCode())
                .isEqualTo(1005);
        assertThat(exception.getErrorCode().getMessage())
                .isEqualTo("User existed");
    }
}
