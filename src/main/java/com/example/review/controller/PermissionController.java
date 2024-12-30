package com.example.review.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.review.dto.request.PermissionRequest;
import com.example.review.dto.response.ApiResponse;
import com.example.review.dto.response.PermissionResponse;
import com.example.review.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @GetMapping("/{name}")
    public ApiResponse<PermissionResponse> get(@PathVariable String name) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.get(name))
                .build();
    }

    @DeleteMapping("/{name}")
    public ApiResponse<?> delete(@PathVariable String name) {
        permissionService.delete(name);
        return ApiResponse.builder().build();
    }
}
