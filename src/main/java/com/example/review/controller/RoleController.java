package com.example.review.controller;

import com.example.review.dto.request.RoleRequest;
import com.example.review.dto.response.ApiResponse;
import com.example.review.dto.response.RoleResponse;
import com.example.review.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @GetMapping("/{name}")
    public ApiResponse<RoleResponse> get(@PathVariable String name) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.get(name))
                .build();
    }

    @DeleteMapping("/{name}")
    public ApiResponse<?> delete(@PathVariable String name) {
        roleService.delete(name);
        return ApiResponse.builder().build();
    }
}
