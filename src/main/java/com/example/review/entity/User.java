package com.example.review.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true, nullable = false)
    String username;
    String password;

    @Column(unique = true, nullable = false)
    String email;
    String firstname;
    String lastname;
    String phone;
    String address;
    LocalDate dob;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles;

    @Builder.Default
    boolean active = true;
}
