package com.example.MC.dto;

import com.example.MC.constant.BoardType;
import com.example.MC.entity.Post;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostDto {
    private Long id;

    @NotNull(message = "게시판을 선택해 주세요")
    private BoardType board;

    @NotEmpty(message =" 제목 필수 입력")
    @Length(max=40, message = "제목 20자 제한")
    private String title;

    @Length(max=4000, message = "본문 2000자 제한")
    private String body;

    private LocalDateTime regTime;

    private String createdBy;

    private List<PostImgDto> postImgDtoList = new ArrayList<>();

    private List<Long> postImgIds =new ArrayList<>();

    public static PostDto of(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setBoard(post.getBoard());
        postDto.setBody(post.getBody());
        postDto.setTitle(post.getTitle());
        postDto.setRegTime(post.getRegTime());
        return postDto;
    }
}
