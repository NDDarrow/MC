package com.example.MC.entity;

import com.example.MC.constant.BoardType;
import com.example.MC.dto.PostDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
public class Post extends BaseEntity{
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BoardType board;

    @Column
    private String nick;
    @Column(nullable = false, length = 40)
    private String title;

    @Column(nullable = false, length = 4000)
    private String body;

    @ManyToOne
    @JoinColumn(name= "member_id")
    private Member member;

    @Column
    int view = 0;
    @Column
    int good = 0;
    @Column
    int bad = 0;
    @Column
    int commentCnt = 0;

    public static Post createPost(PostDto postDto, Member member){
        Post post = new Post();
        post.setBoard(postDto.getBoard());
        post.setTitle(postDto.getTitle());
        post.setBody(postDto.getBody());
        post.setNick(postDto.getNick());
        post.setMember(member);
        return post;
    }
}
