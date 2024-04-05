package com.example.MC.dto;

import com.example.MC.constant.BoardType;
import com.example.MC.entity.APost;
import com.example.MC.entity.Post;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class APostDto {
    private BoardType board = BoardType.ABOARD;

    @NotEmpty(message =" 제목 필수 입력")
    @Length(max=40, message = "제목 20자 제한")
    private String title;

    @Length(max=4000, message = "본문 2000자 제한")
    private String body;

    private int password;

    public static APostDto createDto(APost aPost){
        APostDto aPostDto = new APostDto();
        aPostDto.setTitle(aPost.getTitle());
        aPostDto.setBody(aPost.getBody());
        aPostDto.setPassword(aPost.getPassword());
        return aPostDto;
    }
}