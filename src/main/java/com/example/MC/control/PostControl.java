package com.example.MC.control;

import com.example.MC.constant.BoardType;
import com.example.MC.dto.APostDto;
import com.example.MC.dto.CommentDto;
import com.example.MC.dto.PostDto;
import com.example.MC.entity.Member;
import com.example.MC.entity.Post;
import com.example.MC.service.CommentService;
import com.example.MC.service.MemberService;
import com.example.MC.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostControl {
    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;
    @GetMapping(value = {"/Genre", "/Genre/{page}"})
    public String genreBoard(@PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<PostDto> genreList = postService.getPostList(BoardType.ROCK, pageable);

        model.addAttribute("items", genreList);
        model.addAttribute("maxPage",5);
        return "/board/Genre";
    }
    @GetMapping(value = {"/FindMusic","/FindMusic/{page}"})
    public String FmBoard(@PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<PostDto> FMList = postService.getPostList(BoardType.FINDMUSIC, pageable);

        model.addAttribute("items", FMList);
        model.addAttribute("maxPage",5);
        return "/board/FindMusic";
    }
    @GetMapping(value = {"/FreeBoard", "/FreeBoard/{page}"})
    public String FreeBoard(@PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<PostDto> FreeList = postService.getPostList(BoardType.FREEBOARD, pageable);

        model.addAttribute("items", FreeList);
        model.addAttribute("maxPage",5);
        return "/board/Freeboard";
    }
    @GetMapping(value = {"/News", "/News/{page}"})
    public String newsBoard(@PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<PostDto> NewsList = postService.getPostList(BoardType.KNEWS, pageable);

        model.addAttribute("items", NewsList);
        model.addAttribute("maxPage",5);
        return "/board/News";
    }
    @GetMapping(value = {"/view/{id}","/view/{id}/{page}"})
    public String viewPost(@PathVariable("id") long id,@PathVariable("page") Optional<Integer> page, Model model){
        PostDto postDto = postService.viewPost(id);
        model.addAttribute("post",postDto);

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<CommentDto> commentDtoList = commentService.findComment(id, pageable);
        model.addAttribute("comment", commentDtoList);

        return "/board/view/{id}";
    }

    @PostMapping("/posting")
    public String writePost(@Valid PostDto postDto, BindingResult bindingResult, Principal principal,@RequestParam("postImgFile") List<MultipartFile> multipartFileList, Model model){
       if(bindingResult.hasErrors()){
           return "/posting";
       }
        Member user =memberService.findByEmail(principal.getName());
        Post post = Post.createPost(postDto,user);
       try{
           postService.writePost(post, multipartFileList);
       }catch(Exception e){
           model.addAttribute("errorMessage" ,"게시글 저장 중 오류가 발생했습니다");
           return "/posting";
       }
        return "redirect:/";
    }
    @PostMapping("/Aboard/post")
    public String writeAboard(@Valid APostDto aPostDto, BindingResult bindingResult){
        return null;
    }
}
