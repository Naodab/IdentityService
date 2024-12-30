package com.example.review.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.review.dto.request.UserCreateRequest;
import com.example.review.dto.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Container
    static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:8.0.36");

    @DynamicPropertySource
    static void configureDataSource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driverClassName", MYSQL_CONTAINER::getDriverClassName);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

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
        mvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(userCreateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.username").value("john123"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.firstname").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.lastname").value("Martha"));
    }

    //    @Test
    //    void createUser_usernameInvalid_fail() throws Exception {
    //        // GIVEN
    //        userCreateRequest.setUsername("john");
    //        ObjectMapper mapper = new ObjectMapper();
    //        mapper.registerModule(new JavaTimeModule());
    //
    //        // WHEN, THEN
    //        mvc.perform(MockMvcRequestBuilders
    //                .post("/users")
    //                .contentType(MediaType.APPLICATION_JSON_VALUE)
    //                .content(mapper.writeValueAsString(userCreateRequest)))
    //            .andExpect(MockMvcResultMatchers.status().isBadRequest())
    //            .andExpect(MockMvcResultMatchers.jsonPath("code")
    //                    .value(1002))
    //            .andExpect(MockMvcResultMatchers.jsonPath("message")
    //                    .value("Username must be at least 6 characters."));
    //    }

    //    @Test
    //    void createUser_passwordInvalid_fail() throws Exception {
    //        // GIVEN
    //        userCreateRequest.setPassword("123456");
    //        ObjectMapper mapper = new ObjectMapper();
    //        mapper.registerModule(new JavaTimeModule());
    //
    //        // WHEN, THEN
    //        mvc.perform(MockMvcRequestBuilders
    //                    .post("/users")
    //                    .contentType(MediaType.APPLICATION_JSON_VALUE)
    //                    .content(mapper.writeValueAsString(userCreateRequest)))
    //            .andExpect(MockMvcResultMatchers.status().isBadRequest())
    //            .andExpect(MockMvcResultMatchers.jsonPath("code")
    //                    .value(1003))
    //            .andExpect(MockMvcResultMatchers.jsonPath("message")
    //                    .value("Password must be at least 8 characters."));
    //    }
    //
    //    @Test
    //    void createUser_dobInvalid_fail() throws Exception {
    //        // GIVEN
    //        userCreateRequest.setDob(LocalDate.now());
    //        ObjectMapper mapper = new ObjectMapper();
    //        mapper.registerModule(new JavaTimeModule());
    //
    //        // WHEN, THEN
    //        mvc.perform(MockMvcRequestBuilders
    //                        .post("/users")
    //                        .contentType(MediaType.APPLICATION_JSON_VALUE)
    //                        .content(mapper.writeValueAsString(userCreateRequest)))
    //                .andExpect(MockMvcResultMatchers.status().isBadRequest())
    //                .andExpect(MockMvcResultMatchers.jsonPath("code")
    //                        .value(1004))
    //                .andExpect(MockMvcResultMatchers.jsonPath("message")
    //                        .value("Must at least 18 age."));
    //    }
}
