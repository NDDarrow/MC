package com.example.MC.repository;

import com.example.MC.entity.APost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface APostRepo extends JpaRepository<APost, Long> {
}
