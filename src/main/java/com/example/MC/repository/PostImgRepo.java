package com.example.MC.repository;

import com.example.MC.entity.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PostImgRepo extends JpaRepository<PostImg, Long> {
    List<PostImg> findByPostIdOrderByIdAsc(Long postId);
    PostImg findByPostId(Long postId);
}
