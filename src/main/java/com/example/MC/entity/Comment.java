package com.example.MC.entity;

import com.example.MC.dto.CommentDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
public class Comment extends BaseEntity{
    @Id
    @Column(name="comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private Long cId;

    private String body;

    int good = 0;

    int bad = 0;

    public static Comment createComment(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        return comment;
    }
}
