package com.example.MC.repository;

import com.example.MC.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CommentRepo extends JpaRepository<Comment, Long> {
    public Page<Comment> findByPostIdOrderByIdAsc(Long postId, Pageable pageable);

    public int countByPostId(Long postId);

    public List<Comment> findByCreatedBy(String createdBy);
}
