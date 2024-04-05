package com.example.MC.dto;

import com.example.MC.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private String writer;
    private String body;

    public static CommentDto createDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setWriter(comment.getWriter());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }
}

