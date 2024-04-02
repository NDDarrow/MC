package com.example.MC.repository;

import com.example.MC.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Long> {

}
