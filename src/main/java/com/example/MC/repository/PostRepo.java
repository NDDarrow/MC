package com.example.MC.repository;

import com.example.MC.constant.BoardType;
import com.example.MC.entity.Member;
import com.example.MC.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long>, QuerydslPredicateExecutor<Post>, PostRepoCustom{
    public List<Post> findFirst10ByOrderByGoodDesc();

    public List<Post> findFirst10ByOrderByViewDesc();

    public List<Post> findFirst10ByOrderByCommentCntDesc();

    public List<Post> findFirst10ByBoardOrderByIdDesc(BoardType boardType);

    public Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    public Page<Post> findByMemberId(Long id, Pageable pageable);
}
