package com.example.stc.repositories;

import com.example.stc.domain.Files;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FilesRepository extends JpaRepository<Files, Long> {
}
