package com.example.review.controller;

import com.example.review.dto.request.UserCreateRequest;
import com.example.review.dto.response.UserResponse;
import com.example.review.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    private UserCreateRequest userCreateRequest;
    private UserResponse userResponse;

    @BeforeEach
    void initData() {
        userCreateRequest = UserCreateRequest.builder()
                .username("john")
                .firstname("John")
                .lastname("Martha")
                .password("123456")
                .dob(LocalDate.of(2004, 12, 12))
                .email("john@gmail.com")
                .phone("0905601223")
                .address("Mieu Bong")
                .build();

        userResponse = UserResponse.builder().build();
    }

    @Test
    void createUser() {
        log.info("Test: Hello test");
    }
}
