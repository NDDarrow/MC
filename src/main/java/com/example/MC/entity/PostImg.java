package com.example.MC.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PostImg extends BaseEntity{
    @Id
    @Column(name="post_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String imgName; //이미지이름
    @Column
    private String imgUrl; //이미지 경로
    @Column
    private String oriImgName; // 원본이미지 이름

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;
}
