package com.example.review.dto.request;

import com.example.review.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    String email;
    String firstname;
    String lastname;
    String phone;
    String address;

    @DobConstraint(min = 18, message = "DOB_INVALID")
    LocalDate dob;
    boolean active;
}
