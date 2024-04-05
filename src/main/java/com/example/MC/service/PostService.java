package com.example.MC.service;

import com.example.MC.entity.Member;
import com.example.MC.entity.Post;
import com.example.MC.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final  PostRepo postRepo;
    public void writePost(Post post){
        postRepo.save(post);
    }
}
