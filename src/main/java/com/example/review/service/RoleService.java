package com.example.review.service;

import com.example.review.dto.request.RoleRequest;
import com.example.review.dto.response.RoleResponse;
import com.example.review.entity.Role;
import com.example.review.exception.AppException;
import com.example.review.exception.ErrorCode;
import com.example.review.mapper.RoleMapper;
import com.example.review.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        if (roleRepository.existsById(request.getName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        Role role = roleMapper.toRole(request);
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toRoleResponse).toList();
    }

    public RoleResponse get(String name) {
        return roleMapper.toRoleResponse(roleRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOTE_EXISTED)));
    }

    public void delete(String name) {
        roleRepository.deleteById(name);
    }
}
