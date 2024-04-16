package com.example.MC.entity;

import com.example.MC.constant.BoardType;
import com.example.MC.dto.APostDto;
import com.example.MC.dto.PostDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class APost extends BaseEntity{
    @Id
    @Column(name = "a_post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BoardType board;

    @Column(nullable = false, length = 40)
    private String title;

    @Column(nullable = false, length = 4000)
    private String body;

    private int password;

    @Column
    int view;
    @Column
    int good;
    @Column
    int bad;

    public static APost createPost(APostDto aPostDto){
        APost apost = new APost();
        apost.setBoard(aPostDto.getBoard());
        apost.setTitle(aPostDto.getTitle());
        apost.setBody(aPostDto.getBody());
        apost.setPassword(apost.getPassword());
        apost.setGood(0);
        apost.setBad(0);
        apost.setView(0);
        return apost;
    }

}
