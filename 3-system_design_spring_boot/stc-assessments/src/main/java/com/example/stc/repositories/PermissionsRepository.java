package com.example.stc.repositories;


import com.example.stc.domain.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
    Permissions findByUserEmail(String email);
}
