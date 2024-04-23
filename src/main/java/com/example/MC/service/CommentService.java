package com.example.MC.service;

import com.example.MC.dto.CommentDto;
import com.example.MC.entity.Comment;
import com.example.MC.entity.Member;
import com.example.MC.entity.Post;
import com.example.MC.repository.CommentRepo;
import com.example.MC.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepo commentRepo;
    private final MemberRepo memberRepo;
    public Page<CommentDto> findComment(Long id, Pageable pageable){
        Page<Comment> comments = commentRepo.findByPostIdOrderByIdAsc(id, pageable);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for(Comment comment : comments){
            Member user = memberRepo.findByEmail(comment.getCreatedBy());
            CommentDto commentDto = CommentDto.createDto(comment);
            commentDto.setWriter(user.getUserNick());
            if(commentDto.getCId() != 0L && commentDtoList.size() >= 2){
                for(int i=0; i< commentDtoList.size(); i++){
                    CommentDto temp = commentDtoList.get(i);
                    if( temp.getId() == commentDto.getCId()){
                        int k=i+1;
                        for(;commentDtoList.get(k).getCId() == commentDto.getCId(); k++);
                        commentDtoList.add( k, commentDto);
                        break;
                    }
                }
            } else
                commentDtoList.add(commentDto);

        }
        //Collections.sort(commentDtoList, CommentDto.commentComparator);

        long total = commentRepo.countByPostId(id);

        return new PageImpl<>(commentDtoList, pageable, total);
    }
    public Optional<Comment> findCommentId(Long id){
        return commentRepo.findById(id);
    }

    public void writeComment(Comment comment){
        commentRepo.save(comment);
    }
    public Comment good(long id){
        Comment comment = commentRepo.findById(id).get();
        comment.setGood(comment.getGood()+1);
        return comment;
    }
    public Comment bad(long id){
        Comment comment = commentRepo.findById(id).get();
        comment.setBad(comment.getBad()+1);
        return comment;
    }
    public long deleteComment(long id){
        Comment comment = commentRepo.findById(id).get();
        long post_id = comment.getPost().getId();
        commentRepo.delete(comment);
        return post_id;
    }
    public int myCommentCnt(Member user){
        List<Comment> commentList = commentRepo.findByCreatedBy(user.getEmail());
        int cnt = commentList.size();
        return cnt;
    }
}
