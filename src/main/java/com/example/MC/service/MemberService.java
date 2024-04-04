package com.example.MC.service;

import com.example.MC.dto.MemberDto;
import com.example.MC.entity.Member;
import com.example.MC.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepo memberRepo;


    public void saveMember(MemberDto memberDto, PasswordEncoder passwordEncoder){
        Member member = Member.createMember(memberDto, passwordEncoder);
        validEmail(member);
        memberRepo.save(member);
    }
    private void validEmail(Member member){
        Member find = memberRepo.findByEmail(member.getEmail());
        if(find != null){
            throw new IllegalStateException("이메일 중복");
        }
    }

    public Member findEmail(String name, String tell){
        Member user = memberRepo.findByNameAndTell(name,tell);
        if(user != null){
            return user;
        }else return null;
    }

    public Member findPw(String email, String tell){
        Member user = memberRepo.findByEmailAndTell(email, tell);
        if(user != null){
            return user;
        }else return null;
    }

    public void reSign(String email, String pw){
        Member user = memberRepo.findByEmail(email);
        memberRepo.delete(user);
    }
}
