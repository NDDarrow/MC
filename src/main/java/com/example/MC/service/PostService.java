package com.example.MC.service;

import com.example.MC.constant.BoardType;
import com.example.MC.dto.PostDto;
import com.example.MC.dto.PostImgDto;
import com.example.MC.entity.APostImg;
import com.example.MC.entity.Member;
import com.example.MC.entity.Post;
import com.example.MC.entity.PostImg;
import com.example.MC.repository.APostImgRepo;
import com.example.MC.repository.MemberRepo;
import com.example.MC.repository.PostImgRepo;
import com.example.MC.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


import static com.example.MC.entity.QPost.post;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;
    private final PostImgService postImgService;
    private final PostImgRepo postImgRepo;
    private final MemberRepo memberRepo;
    //글쓰기 메서드
    public void writePost(Post post, List<MultipartFile> multipartFileList) throws Exception{
        postRepo.save(post);
        for( int i =0; i< multipartFileList.size(); i++){
            PostImg postImg = new PostImg();
            postImg.setPost(post);
            postImgService.savePostImg(postImg,multipartFileList.get(i));
        }
    }
    //게시판 입장 페이징 메서드
    public Page<PostDto> getPostList(BoardType boardType, Pageable pageable){
        Page<Post> postPage =  postRepo.getPostPage(boardType, pageable);
        List<PostDto> postDtoPage = new ArrayList<PostDto>();
        for(Post post : postPage){
            Member user = memberRepo.findByEmail(post.getCreatedBy());
            PostDto postDto = PostDto.of(post);
            postDto.setCreatedBy(user.getUserNick());
            postDtoPage.add(postDto);
        }
        return new PageImpl<>(postDtoPage, pageable, postPage.getTotalPages());
    }
    public PostDto viewPost(long postId){
        List<PostImg> postImgs = postImgRepo.findByPostIdOrderByIdAsc(postId);
        List<PostImgDto> postImgDtoList = new ArrayList<>();
        for(PostImg postImg : postImgs){
            PostImgDto postImgDto = PostImgDto.of(postImg);
            postImgDtoList.add(postImgDto);
        }
        Post post = postRepo.findById(postId).get();
        Member user = memberRepo.findByEmail(post.getCreatedBy());
        PostDto postDto = PostDto.of(post);
        postDto.setCreatedBy(user.getUserNick());
        postDto.setPostImgDtoList(postImgDtoList);
        return postDto;
    }

    public List<PostDto> getHitList(){
        List<Post> hit = postRepo.findFirst10ByOrderByGoodDesc();
        List<PostDto> hitList = new ArrayList<>();
        for(Post post : hit){
            Member user = memberRepo.findByEmail(post.getCreatedBy());
            PostDto postDto =  PostDto.of(post);
            postDto.setCreatedBy(user.getUserNick());
            hitList.add(postDto);
        }
        return hitList;
    }
    public List<PostDto> getViewList(){
        List<Post> view = postRepo.findFirst10ByOrderByViewDesc();
        List<PostDto> viewList = new ArrayList<>();
        for(Post post : view){
            Member user = memberRepo.findByEmail(post.getCreatedBy());
            PostDto postDto =  PostDto.of(post);
            postDto.setCreatedBy(user.getUserNick());
            viewList.add(postDto);
        }
        return viewList;
    }
    public List<PostDto> getCommentList(){
        List<Post> comment = postRepo.findFirst10ByOrderByCommentCntDesc();
        List<PostDto> commentList = new ArrayList<>();
        for(Post post : comment){
            Member user = memberRepo.findByEmail(post.getCreatedBy());
            PostDto postDto =  PostDto.of(post);
            postDto.setCreatedBy(user.getUserNick());
            commentList.add(postDto);
        }
        return commentList;
    }
    public List<PostDto> getNewsList(){
        List<Post> news = postRepo.findFirst10ByBoardOrderByIdDesc(BoardType.KNEWS);
        List<PostDto> newsList = new ArrayList<>();
        for(Post post : news){
            Member user = memberRepo.findByEmail(post.getCreatedBy());
            PostDto postDto =  PostDto.of(post);
            postDto.setCreatedBy(user.getUserNick());
            newsList.add(postDto);
        }
        return newsList;
    }
    public Post findPost(Long id){
        Post post = postRepo.findById(id).get();
        return post;
    }
}
