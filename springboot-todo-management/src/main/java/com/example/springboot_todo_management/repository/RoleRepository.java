package com.example.springboot_todo_management.repository;

import com.example.springboot_todo_management.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
