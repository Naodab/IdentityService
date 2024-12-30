package com.example.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.review.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
