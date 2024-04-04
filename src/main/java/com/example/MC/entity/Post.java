package com.example.MC.entity;

import com.example.MC.constant.BoardType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@ToString
public class Post {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BoardType board;

    @Column(nullable = false, length = 40)
    private String title;

    @Column(nullable = false, length = 4000)
    private String body;

    @ManyToOne
    @JoinColumn(name= "member_id")
    private Member member;

    @Column
    int view;
    @Column
    int good;
    @Column
    int bad;


}
