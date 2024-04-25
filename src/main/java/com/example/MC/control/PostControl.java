package com.example.MC.control;

import com.example.MC.constant.BoardType;
import com.example.MC.dto.APostDto;
import com.example.MC.dto.CommentDto;
import com.example.MC.dto.ItemSearchDto;
import com.example.MC.dto.PostDto;
import com.example.MC.entity.Comment;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
    //장르 게시판 이동
    @GetMapping(value = {"/Genre/{genre}", "/Genre/{genre}/{page}"})
    public String genreBoard(@PathVariable("genre") String genre, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        // 장르에 맞는 글 리스트를 가져오는 서비스 메서드를 호출합니다
        Page<PostDto> genreList = postService.getPostList(BoardType.valueOf(genre), pageable);
        model.addAttribute("items", genreList);
        model.addAttribute("maxPage",5);
        model.addAttribute("board","genre");
        return "/board/Genre";
    }

    //음악찾기 게시판 이동
    @GetMapping(value = {"/FindMusic","/FindMusic/{page}"})
    public String FmBoard(@PathVariable(name="page",required = false) Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<PostDto> FMList = postService.getPostList(BoardType.FINDMUSIC, pageable);

        model.addAttribute("items", FMList);
        model.addAttribute("maxPage",5);
        model.addAttribute("board","findMusic");
        return "/board/FindMusic";
    }
    //자유게시판 이동
    @GetMapping(value = {"/FreeBoard/{genre}", "/FreeBoard/{genre}/{page}"})
    public String FreeBoard(@PathVariable("genre") String genre, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<PostDto> FreeList = postService.getPostList(BoardType.valueOf(genre), pageable);

        model.addAttribute("items", FreeList);
        model.addAttribute("maxPage",5);
        model.addAttribute("board","freeBoard");
        return "/board/Freeboard";
    }
    //뉴스게시판 이동
    @GetMapping(value = {"/News/{genre}", "/News/{genre}/{page}"})
    public String newsBoard(@PathVariable("genre") String genre, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<PostDto> NewsList = postService.getPostList(BoardType.valueOf(genre), pageable);

        model.addAttribute("items", NewsList);
        model.addAttribute("maxPage",5);
        model.addAttribute("board","news");
        return "/board/News";
    }
    @GetMapping(value = {"/SC/{genre}", "/SC/{genre}/{page}"})
    public String SCBoard(@PathVariable("genre") String genre, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<PostDto> SCList = postService.getPostList(BoardType.valueOf(genre), pageable);

        model.addAttribute("items", SCList);
        model.addAttribute("maxPage", 5);
        model.addAttribute("board", "SC");
        return "/board/SC";
    }
    //게시글 보기
    @GetMapping(value = {"/view/{id}","/view/{id}/{page}"})
    public String viewPost(@PathVariable("id") long id,@PathVariable("page") Optional<Integer> page, Model model, Principal principal){
        PostDto postDto = postService.viewPost(id);
        model.addAttribute("post",postDto);
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

        Page<CommentDto> commentDtoList = commentService.findComment(id, pageable);
        model.addAttribute("items", commentDtoList);
        model.addAttribute("maxPage",5);
        model.addAttribute("commentDto", new CommentDto());
        model.addAttribute("postId",id);
        if(principal != null){
            model.addAttribute("principal",principal.getName());
        }
        return "/board/view";
    }
    //게시글 작성 이동
    @GetMapping("/posting")
    public String writeForm(Model model){
        model.addAttribute("postDto", new PostDto());
        return "/board/PostForm";
    }
    //게시글 작성
    @PostMapping("/posting")
    public String writePost(@Valid PostDto postDto, BindingResult bindingResult, Principal principal,@RequestParam("postImgFile") List<MultipartFile> multipartFileList, Model model, HttpServletRequest request){
       if(bindingResult.hasErrors()){
           return "/board/PostForm";
       }
        Member user =memberService.findByEmail(principal.getName());
       try{
           postService.writePost(postDto, user, multipartFileList);
       }catch(Exception e){
           model.addAttribute("errorMessage" ,"게시글 저장 중 오류가 발생했습니다");
           return "/board/PostForm";
       }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/postUpdate")
    public String updatePost(@Valid PostDto postDto,BindingResult bindingResult, @RequestParam("postImgFile") List<MultipartFile> multipartFileList, Model model, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "/board/PostUpdate";
        }
        try {
            postService.updatePosting(postDto, multipartFileList);
        }catch(Exception e){
            model.addAttribute("errorMessage" ,"게시글 저장 중 오류가 발생했습니다");
            e.printStackTrace();
            return "/board/PostForm";
        }
        redirectAttributes.addAttribute("postId", postDto.getId());
        return "redirect:/board/view/{postId}";
    }
    //익명 게시글 작성
    @PostMapping("/Aboard/post")
    public String writeAboard(@Valid APostDto aPostDto, BindingResult bindingResult){
        return null;
    }

    //댓글 작성
    @PostMapping("{id}/comment")
    public String writeComment(@PathVariable("id") String postId,@RequestParam("body") String body, BindingResult bindingResult,Principal principal){
        long id = Long.parseLong(postId);
        CommentDto commentDto = new CommentDto();
        commentDto.setBody(body);
        commentDto.setWriter(principal.getName());
        Comment comment = Comment.createComment(commentDto);
        if(bindingResult.hasErrors()){
            return "redirect:/board/view/"+id;
        }
        Post post = postService.findPost(id);
        comment.setPost(post);
        commentService.writeComment(comment);
        post.setCommentCnt(post.getCommentCnt()+1);
        postService.updatePost(post);
        return "redirect:/board/view/"+id;
    }
    //대댓글 작성
    @PostMapping("{id}/comment/{cId}")
    public String writeReply(@PathVariable("id") String postId, @PathVariable("cId") String cId,@RequestParam("comment-body") String body){
        long id = Long.parseLong(postId);
        long commentId= Long.parseLong(cId);
        Comment comment = new Comment();
        comment.setBody(body);
        Post post = postService.findPost(id);
        comment.setPost(post);
        Comment motherComment = commentService.findCommentId(commentId).get();
        comment.setCId(motherComment.getId());

        commentService.writeComment(comment);
        post.setCommentCnt(post.getCommentCnt()+1);
        return "redirect:/board/view/"+id;
    }
    //좋아요
    @GetMapping("/view/{id}/good")
    public String good(@PathVariable("id") String postId){
        long id = Long.parseLong(postId);
        postService.good(id);
        return "redirect:/board/view/"+id;
    }
    //싫어요
    @GetMapping("/view/{id}/bad")
    public String bad(@PathVariable("id") String postId){
        long id = Long.parseLong(postId);
        postService.bad(id);
        return "redirect:/board/view/"+id;
    }
    @GetMapping("/view/{id}/update")
    public String postUpdate(@PathVariable("id") long postId, Model model){
        Post post = postService.findPost(postId);
        PostDto postDto = PostDto.of(post);
        model.addAttribute("postDto", postDto);
        return "/board/PostUpdate";
    }
    @GetMapping("/view/{id}/delete")
    public String postDelete(@PathVariable("id") String postId){
        long id = Long.parseLong(postId);
        postService.deletePost(id);
        return "/";
    }
    @PostMapping()
    @GetMapping("/view/{id}/cGood")
    public String cGood(@PathVariable("id") String commentId){
        long id = Long.parseLong(commentId);
        Comment comment = commentService.good(id);

        return "redirect:/board/view/"+comment.getPost().getId();
    }
    //싫어요
    @GetMapping("/view/{id}/cBad")
    public String cBad(@PathVariable("id") String postId){
        long id = Long.parseLong(postId);
        Comment comment = commentService.bad(id);
        return "redirect:/board/view/"+comment.getPost().getId();
    }
    @GetMapping("/view/{id}/cDelete")
    public String commentDelete(@PathVariable("id") String postId){
        long id = Long.parseLong(postId);
        long post_id = commentService.deleteComment(id);
        return "redirect:/board/view/"+post_id;
    }
}
