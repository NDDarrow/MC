package com.example.MC.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
public class Member {
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @OneToMany(mappedBy = "followers")
    private List<Follower> followers = new ArrayList<>();


    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private Long tell;

    @Column
    private String zipCode; //우편번호

    @Column
    private String addr1; //기본주소

    @Column
    private  String addr2; // 상세주소
}
