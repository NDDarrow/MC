package com.example.MC.repository;

import com.example.MC.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepo extends JpaRepository<Follower, Long> {
    Follower findByUserIdAndMemberId(long usetId, long memberId);
}
