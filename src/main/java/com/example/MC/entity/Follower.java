package com.example.MC.entity;

import com.example.MC.dto.FollowerDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.ui.Model;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    public static  Follower createFollower(FollowerDto followerDto, Member member){
        Follower follower = new Follower();
        follower.setUserId(follower.getUserId());
        return follower;
    }
}


