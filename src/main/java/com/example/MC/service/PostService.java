package com.example.MC.service;

import com.example.MC.constant.BoardType;
import com.example.MC.dto.PostDto;
import com.example.MC.dto.PostImgDto;
import com.example.MC.entity.APostImg;
import com.example.MC.entity.Member;
import com.example.MC.entity.Post;
import com.example.MC.entity.PostImg;
import com.example.MC.repository.APostImgRepo;
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
            PostDto postDto = PostDto.of(post);
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
        PostDto postDto = PostDto.of(post);
        postDto.setPostImgDtoList(postImgDtoList);
        return postDto;
    }

    public List<PostDto> getHitList(){
        List<Post> hit = postRepo.findFirst10ByOrderByGoodDesc();
        List<PostDto> hitList = new ArrayList<>();
        for(Post post : hit){
            PostDto postDto =  PostDto.of(post);
            hitList.add(postDto);
        }
        return hitList;
    }
    public List<PostDto> getViewList(){
        List<Post> view = postRepo.findFirst10ByOrderByViewDesc();
        List<PostDto> viewList = new ArrayList<>();
        for(Post post : view){
            PostDto postDto =  PostDto.of(post);
            viewList.add(postDto);
        }
        return viewList;
    }
    public List<PostDto> getCommentList(){
        List<Post> comment = postRepo.findFirst10ByOrderByCommentCntDesc();
        List<PostDto> commentList = new ArrayList<>();
        for(Post post : comment){
            PostDto postDto =  PostDto.of(post);
            commentList.add(postDto);
        }
        return commentList;
    }
    public List<PostDto> getNewsList(){
        List<Post> news = postRepo.findFirst10ByBoardOrderByIdDesc(BoardType.KNEWS);
        List<PostDto> newsList = new ArrayList<>();
        for(Post post : news){
            PostDto postDto =  PostDto.of(post);
            newsList.add(postDto);
        }
        return newsList;
    }

}
