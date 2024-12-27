package com.example.review.service;

import com.example.review.dto.request.PermissionRequest;
import com.example.review.dto.response.PermissionResponse;
import com.example.review.entity.Permission;
import com.example.review.exception.AppException;
import com.example.review.exception.ErrorCode;
import com.example.review.mapper.PermissionMapper;
import com.example.review.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        if (permissionRepository.existsById(request.getName()))
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse).toList();
    }

    public PermissionResponse get(String id) {
        return permissionMapper.toPermissionResponse(permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED)));
    }

    public void delete(String name) {
        permissionRepository.deleteById(name);
    }
}
