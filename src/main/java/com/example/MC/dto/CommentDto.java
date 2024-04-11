package com.example.MC.dto;

import com.example.MC.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private String writer;
    private String body;
    private int good;
    private int bad;

    public static CommentDto createDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setWriter(comment.getCreatedBy());
        commentDto.setBody(comment.getBody());
        commentDto.setGood(commentDto.getGood());
        commentDto.setBad(commentDto.getBad());
        return commentDto;
    }
}

