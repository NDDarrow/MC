package com.example.MC.dto;

import com.example.MC.entity.Follower;
import com.example.MC.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter @Setter
public class FollowerDto {
    private Long userId;

    private Long followerId;

    public static FollowerDto createFDto(Follower follower){
        FollowerDto followerDto = new FollowerDto();
        followerDto.setUserId(follower.getUserId());
        followerDto.setFollowerId(follower.getMember().getId());
        return followerDto;
    }
}
