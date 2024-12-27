package com.example.review.mapper;

import com.example.review.dto.request.PermissionRequest;
import com.example.review.dto.response.PermissionResponse;
import com.example.review.entity.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {
    public Permission toPermission(PermissionRequest request) {
        return Permission.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public PermissionResponse toPermissionResponse(Permission permission) {
        return PermissionResponse.builder()
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }
}
