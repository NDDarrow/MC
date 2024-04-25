package com.example.MC.repository;

import com.example.MC.entity.Follower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepo extends JpaRepository<Follower, Long> {
    Follower findByUserIdAndMemberId(long userId, long memberId);

    Page<Follower> findByUserId(long userId , Pageable pageable);
}
