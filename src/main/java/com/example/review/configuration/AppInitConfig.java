package com.example.review.configuration;

import com.example.review.constant.PredefinedRole;
import com.example.review.entity.Role;
import com.example.review.entity.User;
import com.example.review.repository.RoleRepository;
import com.example.review.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppInitConfig {
    static final String ADMIN_USERNAME = "admin";
    static final String ADMIN_PASSWORD = "adminadmin";
    static final String ADMIN_EMAIL = "admin@admin.com";
    PasswordEncoder encoder;

    @Bean
    @ConditionalOnProperty(prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner init(UserRepository userRepository,
                           RoleRepository roleRepository) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                roleRepository.save(Role.builder()
                        .name(PredefinedRole.ROLE_USER)
                        .description("role user")
                        .build());
                Role adminRole = roleRepository.save(Role.builder()
                        .name(PredefinedRole.ROLE_ADMIN)
                        .description("role admin")
                        .build());
                var roles = new HashSet<Role>();
                roles.add(adminRole);
                userRepository.save(User.builder()
                        .username(ADMIN_USERNAME)
                        .password(encoder.encode(ADMIN_PASSWORD))
                        .email(ADMIN_EMAIL)
                        .roles(roles)
                        .build());
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
