package com.example.review.mapper;

import com.example.review.dto.request.RoleRequest;
import com.example.review.dto.response.RoleResponse;
import com.example.review.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public Role toRole(RoleRequest request) {
        return Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public RoleResponse toRoleResponse(Role role) {
        return RoleResponse.builder()
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }
}
