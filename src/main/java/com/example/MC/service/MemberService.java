package com.example.MC.service;

import com.example.MC.dto.FollowerDto;
import com.example.MC.dto.MemberDto;
import com.example.MC.entity.Follower;
import com.example.MC.entity.Member;
import com.example.MC.repository.FollowerRepo;

import com.example.MC.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static com.example.MC.entity.Member.resetPw;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepo memberRepo;
    private final FollowerRepo followerRepo;

    public void makeFollowShip(Member user, Member member){
        Follower follower = new Follower();
        follower.setMember(member);
        follower.setUserId(user.getId());
        followerRepo.save(follower);
    }
    public void deleteFollowShip(FollowerDto followerDto){
        Follower follower = followerRepo.findByUserIdAndMemberId(followerDto.getUserId(),followerDto.getFollowerId());
        followerRepo.delete(follower);
    }
    public Follower findFollowShip(long user ,Member follower){
        Follower follower1 = followerRepo.findByUserIdAndMemberId(user, follower.getId());
        return follower1;

    }
    public Optional<Member> findById(long id){
        return memberRepo.findById(id);
    }

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
    public void resetPW(String email, String tell, String password, PasswordEncoder passwordEncoder){
        Member user = memberRepo.findByEmailAndTell(email, tell);
        resetPw(user, password, passwordEncoder);
    }

    public void reSign(String email){
        Member user = memberRepo.findByEmail(email);
        memberRepo.delete(user);
    }

    public Member findByEmail(String email){
        Member user = memberRepo.findByEmail(email);
        return user;
    }

    public void updateMember(MemberDto memberDto){
        Member user = memberRepo.findByEmail(memberDto.getEmail());
        if(user.getUserNick() != memberDto.getUserNick() || memberDto.getUserNick() != null)
            user.setUserNick(memberDto.getUserNick());
        if(user.getName() != memberDto.getName() || memberDto.getName() != null)
            user.setName(memberDto.getName());
        if(user.getTell() != memberDto.getTell() || memberDto.getTell() != null)
            user.setTell(memberDto.getTell());
        if(user.getZipCode() != memberDto.getZipCode() || memberDto.getZipCode() != null)
            user.setZipCode(memberDto.getZipCode());
        if(user.getAddr1() != memberDto.getAddr1() || memberDto.getAddr1() != null) {
            user.setAddr1(memberDto.getAddr1());
            user.setAddr2(memberDto.getAddr2());
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 스프링 시큐리티 사용시 커스텀로그인 DB의 데이터로 로그인 진행시
        // 데이터로 로그인 진행시 필요한 메서드
        Member member = memberRepo.findByEmail(username);
        if( member == null){
            throw new UsernameNotFoundException(username);
        }
        return User.builder()
                .username(member.getEmail() )
                .password(member.getPassword() )
                .roles(member.getRole().toString() ).build();

    }
}
