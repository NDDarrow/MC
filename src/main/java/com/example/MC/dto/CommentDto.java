package com.example.MC.dto;

import com.example.MC.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String writer;
    private String body;
    private LocalDateTime regTime;
    private int good;
    private int bad;
    private String createBy;

    public static CommentDto createDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setBody(comment.getBody());
        commentDto.setGood(comment.getGood());
        commentDto.setBad(comment.getBad());
        commentDto.setRegTime(comment.getRegTime());
        commentDto.setCreateBy(comment.getCreatedBy());
        commentDto.setId(commentDto.getId());
        return commentDto;
    }
}

