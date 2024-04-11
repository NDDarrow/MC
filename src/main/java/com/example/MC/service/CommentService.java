package com.example.MC.service;

import com.example.MC.dto.CommentDto;
import com.example.MC.entity.Comment;
import com.example.MC.entity.Member;
import com.example.MC.entity.Post;
import com.example.MC.repository.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepo commentRepo;
    public Page<CommentDto> findComment(Long id, Pageable pageable){
        Page<Comment> comments = commentRepo.findByPostIdOrderByIdDesc(id, pageable);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for(Comment comment : comments){
            CommentDto commentDto = CommentDto.createDto(comment);
            commentDtoList.add(commentDto);
        }
        long total = commentRepo.countByPostId(id);

        return new PageImpl<>(commentDtoList, pageable, total);
    }
}
