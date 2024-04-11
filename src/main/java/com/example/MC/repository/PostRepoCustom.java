package com.example.MC.repository;

import com.example.MC.constant.BoardType;
import com.example.MC.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepoCustom {
    Page<Post> getPostPage(BoardType boardType, Pageable pageable);
}
