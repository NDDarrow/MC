package com.example.MC.entity;

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

    @Column
    private String board;
    @Column
    private String title;
    @Column
    @ManyToOne
    @JoinColumn(name= "member_id")
    private Long writer;

    @Column
    int view;
    @Column
    int good;
    @Column
    int bad;


}
