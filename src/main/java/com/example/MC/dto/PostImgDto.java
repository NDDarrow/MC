package com.example.MC.dto;

import com.example.MC.entity.PostImg;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PostImgDto {
    private Long id;
    private String imgName;
    private String imgUrl;
    private String oriImgName;


    public static PostImgDto of(PostImg postImg){
        PostImgDto postImgDto = new PostImgDto();
        postImgDto.setImgName(postImg.getImgName());
        postImgDto.setImgUrl(postImg.getImgUrl());
        postImgDto.setOriImgName(postImg.getOriImgName());
        return postImgDto;
    }
}
