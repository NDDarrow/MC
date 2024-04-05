package com.example.MC.dto;

import com.example.MC.constant.BoardType;
import com.example.MC.entity.Post;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PostDto {
    @NotEmpty(message = "게시판을 선택해 주세요")
    private BoardType board;

    @NotEmpty(message =" 제목 필수 입력")
    @Length(max=40, message = "제목 20자 제한")
    private String title;

    @Length(max=4000, message = "본문 2000자 제한")
    private String body;

    public static PostDto createDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setBoard(post.getBoard());
        postDto.setTitle(post.getTitle());
        postDto.setBody(post.getBody());
        return postDto;
    }
}
