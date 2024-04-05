package com.example.MC.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.ui.Model;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}

