package com.example.MC.repository;

import com.example.MC.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Long> {
    Member findByEmail(String email);

    Member findByNameAndTell(String name,String tell);

    Member findByEmailAndTell(String email, String tell);
}
