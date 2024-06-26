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
public class PostDto implements Comparable<PostDto> {
    private Long id;

    @NotNull(message = "게시판을 선택해 주세요")
    private BoardType board;

    @NotEmpty(message ="제목은 필수입니다")
    @Length(max=40, message = "제목 20자 제한")
    private String title;
    @NotEmpty(message="본문이 존재해야 합니다")
    @Length(max=4000, message = "본문 2000자 제한")
    private String body;

    private LocalDateTime regTime;

    private String nick;
    private String createdBy;

    private List<PostImgDto> postImgDtoList = new ArrayList<>();

    private List<Long> postImgIds =new ArrayList<>();

    private int good;

    private int bad;
    private int view;
    private int commentCnt;


    public static PostDto of(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setBoard(post.getBoard());
        postDto.setBody(post.getBody());
        postDto.setTitle(post.getTitle());
        postDto.setRegTime(post.getRegTime());
        postDto.setGood(post.getGood());
        postDto.setBad(post.getBad());
        postDto.setView(post.getView());
        postDto.setNick(post.getNick());
        postDto.setCreatedBy(post.getCreatedBy());
        postDto.setCommentCnt(post.getCommentCnt());
        return postDto;
    }
    @Override
    public int compareTo(PostDto post){
       return this.getRegTime().compareTo(post.getRegTime());
    }
}
