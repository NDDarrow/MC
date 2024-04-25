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
    public void writePost(PostDto postDto,Member user, List<MultipartFile> multipartFileList) throws Exception{
        Post post = Post.createPost(postDto, user);
        postRepo.save(post);
        if(!multipartFileList.isEmpty()) {
            for (int i = 0; i < multipartFileList.size(); i++) {
                MultipartFile file = multipartFileList.get(i);
                if(!file.isEmpty()) {
                    PostImg postImg = new PostImg();
                    postImg.setPost(post);
                    postImgService.savePostImg(postImg, file);
                }
            }
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
        post.setView(post.getView()+1);
        PostDto postDto = PostDto.of(post);
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
    public Post findPost(long id){
        Post post = postRepo.findById(id).get();
        return post;
    }
    public void good(long id){
        Post post = postRepo.findById(id).get();
        post.setGood(post.getGood()+1);
    }
    public void bad(long id){
        Post post = postRepo.findById(id).get();
        post.setBad(post.getBad()+1);
    }
    public Page<PostDto> searchPost(String query, Pageable pageable){
        Page<Post> postPage =  postRepo.findByTitleContaining(query, pageable);
        List<PostDto> postDtoPage = new ArrayList<PostDto>();
        for(Post post : postPage){
            Member user = memberRepo.findByEmail(post.getCreatedBy());
            PostDto postDto = PostDto.of(post);
            postDto.setCreatedBy(user.getUserNick());
            postDtoPage.add(postDto);
        }
        return new PageImpl<>(postDtoPage, pageable, postPage.getTotalPages());
    }
    public void updatePost(Post post){
        postRepo.save(post);
    }

    public void updatePosting(PostDto postDto ,List<MultipartFile> multipartFileList) throws Exception {
        System.out.println(1);
        Post post = postRepo.findById(postDto.getId()).get();
        System.out.println(2);
        post.setBody(postDto.getBody());
        System.out.println(3);
        if(!multipartFileList.isEmpty()) {
            for (int i = 0; i < multipartFileList.size(); i++) {
                MultipartFile file = multipartFileList.get(i);
                if(!file.isEmpty()) {
                    PostImg postImg = new PostImg();
                    postImg.setPost(post);
                    postImgService.updatePostImg(postImg, file);
                }
            }
        }
    }

    public void deletePost(long id){
        Post post = postRepo.findById(id).get();
        postRepo.delete(post);
    }
    public Page<PostDto> getMyList(Member user, Pageable pageable){
        Page<Post> postPage = postRepo.findByMemberId(user.getId(), pageable);
        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : postPage){
            PostDto postDto = PostDto.of(post);
            postDtoList.add(postDto);
        }
        return new PageImpl<>(postDtoList, pageable, postPage.getTotalPages());
    }
    public int myPostCnt(Member user){
        List<Post> postList = postRepo.findByMemberId(user.getId());
        int cnt = postList.size();
        return cnt;
    }
}
