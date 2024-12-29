package com.example.review.controller;

import com.example.review.dto.request.UserCreateRequest;
import com.example.review.dto.response.UserResponse;
import com.example.review.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
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
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Mockito.when(userService.create(ArgumentMatchers.any()))
            .thenReturn(userResponse);

        mvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper
                    .writeValueAsString(userCreateRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("code")
                    .value(1000))
            .andExpect(MockMvcResultMatchers.jsonPath("result.id")
                    .value("cf0600f3538b3"));
    }

    @Test
    void createUser_usernameInvalid_fail() throws Exception {
        // GIVEN
        userCreateRequest.setUsername("john");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // WHEN, THEN
        mvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(userCreateRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("code")
                    .value(1002))
            .andExpect(MockMvcResultMatchers.jsonPath("message")
                    .value("Username must be at least 6 characters."));
    }

    @Test
    void createUser_passwordInvalid_fail() throws Exception {
        // GIVEN
        userCreateRequest.setPassword("123456");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // WHEN, THEN
        mvc.perform(MockMvcRequestBuilders
                    .post("/users")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(mapper.writeValueAsString(userCreateRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("code")
                    .value(1003))
            .andExpect(MockMvcResultMatchers.jsonPath("message")
                    .value("Password must be at least 8 characters."));
    }

    @Test
    void createUser_dobInvalid_fail() throws Exception {
        // GIVEN
        userCreateRequest.setDob(LocalDate.now());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // WHEN, THEN
        mvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(userCreateRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("Must at least 18 age."));
    }
}
