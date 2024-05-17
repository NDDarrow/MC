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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @GetMapping(value = {"/Genre"})
    public String genreBoard(@RequestParam("genre") String genre, @RequestParam("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        // 장르에 맞는 글 리스트를 가져오는 서비스 메서드를 호출합니다
        Page<PostDto> genreList = postService.getPostList(BoardType.valueOf(genre), pageable);
        model.addAttribute("items", genreList);
        model.addAttribute("maxPage",5);
        model.addAttribute("board","genre");
        model.addAttribute("tag",genre );
        return "board/Genre";
    }

    //음악찾기 게시판 이동
    @GetMapping(value = {"/FindMusic"})
    public String FmBoard(@RequestParam(name="page",required = false) Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<PostDto> FMList = postService.getPostList(BoardType.FINDMUSIC, pageable);

        model.addAttribute("items", FMList);
        model.addAttribute("maxPage",5);
        model.addAttribute("board","findMusic");
        return "board/FindMusic";
    }
    //자유게시판 이동
    @GetMapping(value = {"/FreeBoard"})
    public String FreeBoard(@RequestParam("genre") String genre, @RequestParam("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<PostDto> FreeList = postService.getPostList(BoardType.valueOf(genre), pageable);

        model.addAttribute("items", FreeList);
        model.addAttribute("maxPage",5);
        model.addAttribute("board","freeBoard");
        model.addAttribute("tag",genre );
        return "board/FreeBoard";
    }
    //뉴스게시판 이동
    @GetMapping(value = {"/News"})
    public String newsBoard(@RequestParam("genre") String genre, @RequestParam("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<PostDto> NewsList = postService.getPostList(BoardType.valueOf(genre), pageable);

        model.addAttribute("items", NewsList);
        model.addAttribute("maxPage",5);
        model.addAttribute("board","news");
        model.addAttribute("tag",genre );
        return "board/News";
    }
    @GetMapping(value = {"/SC"})
    public String SCBoard(@RequestParam("genre") String genre, @RequestParam("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<PostDto> SCList = postService.getPostList(BoardType.valueOf(genre), pageable);

        model.addAttribute("items", SCList);
        model.addAttribute("maxPage", 5);
        model.addAttribute("board", "SC");
        model.addAttribute("tag",genre );
        return "board/SC";
    }
    //게시글 보기
    @GetMapping(value = {"/view","/view/{page}"})
    public String viewPost(@RequestParam("id") long id,@RequestParam("page") Optional<Integer> page, Model model, Principal principal){
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
        return "board/view";
    }
    //게시글 작성 이동
    @GetMapping("/posting")
    public String writeForm(Model model){
        model.addAttribute("postDto", new PostDto());
        return "board/PostForm";
    }
    //게시글 작성
    @PostMapping("/posting")
    public String writePost(@Valid PostDto postDto, BindingResult bindingResult, Principal principal,@RequestParam("postImgFile") List<MultipartFile> multipartFileList, Model model, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "board/PostForm";
        }
        Member user =memberService.findByEmail(principal.getName());
        try{
            postService.writePost(postDto, user, multipartFileList);
        }catch(Exception e){
            model.addAttribute("errorMessage" ,"게시글 저장 중 오류가 발생했습니다");
            return "board/PostForm";
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/postUpdate")
    public String updatePost(@Valid PostDto postDto,BindingResult bindingResult, @RequestParam("postImgFile") List<MultipartFile> multipartFileList, Model model, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "board/PostUpdate";
        }
        try {
            postService.updatePosting(postDto, multipartFileList);
        }catch(Exception e){
            model.addAttribute("errorMessage" ,"게시글 저장 중 오류가 발생했습니다");
            e.printStackTrace();
            return "board/PostForm";
        }
        redirectAttributes.addAttribute("postId", postDto.getId());
        return "redirect:board/view/{postId}";
    }
    //익명 게시글 작성
    @PostMapping("/Aboard/post")
    public String writeAboard(@Valid APostDto aPostDto, BindingResult bindingResult){
        return null;
    }

    //댓글 작성
    @PostMapping("/comment")
    public String writeComment(@RequestParam("id") String postId,@RequestParam("body") String body,Principal principal){
        long id = Long.parseLong(postId);
        CommentDto commentDto = new CommentDto();
        commentDto.setBody(body);
        commentDto.setWriter(principal.getName());
        Comment comment = Comment.createComment(commentDto);
        Post post = postService.findPost(id);
        comment.setPost(post);
        commentService.writeComment(comment);
        post.setCommentCnt(post.getCommentCnt()+1);
        postService.updatePost(post);
        return "redirect:board/view?id="+postId;
    }
    //대댓글 작성
    @PostMapping("/reply")
    public String writeReply(@RequestParam("id") String postId, @RequestParam("cId") String cId,@RequestParam("comment-body") String body){
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
        return "redirect:board/view?id="+postId;
    }
    //좋아요
    @GetMapping("/good/{id}")
    public String good(@PathVariable("id") String postId,HttpServletRequest request, HttpServletResponse response){
        long id = Long.parseLong(postId);

        Cookie[] cookies = request.getCookies();
        int goodCount = 1;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("good".equals(cookie.getName())) {
                    goodCount += Integer.parseInt(cookie.getValue());
                    break;
                }
            }
        }
        if( goodCount <= 10) {
            Cookie cookie = new Cookie("good", Integer.toString(goodCount));
            cookie.setMaxAge(3600); // 쿠키의 유효 시간 설정 (초 단위, 여기서는 1시간)
            cookie.setPath("/"); // 쿠키의 경로 설정 (모든 경로에 쿠키를 사용할 수 있도록 설정)
            postService.good(id);
            // 응답에 쿠키 추가
            response.addCookie(cookie);
        }
        return "redirect:board/view?id="+postId;
    }
    //싫어요
    @GetMapping("/bad/{id}")
    public String bad(@PathVariable("id") String postId,HttpServletRequest request, HttpServletResponse response){
        long id = Long.parseLong(postId);
        Cookie[] cookies = request.getCookies();
        int goodCount = 1;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("bad".equals(cookie.getName())) {
                    goodCount += Integer.parseInt(cookie.getValue());
                    break;
                }
            }
        }
        if( goodCount <= 10) {
            Cookie cookie = new Cookie("bad", Integer.toString(goodCount));
            cookie.setMaxAge(3600); // 쿠키의 유효 시간 설정 (초 단위, 여기서는 1시간)
            cookie.setPath("/"); // 쿠키의 경로 설정 (모든 경로에 쿠키를 사용할 수 있도록 설정)
            postService.bad(id);
            // 응답에 쿠키 추가
            response.addCookie(cookie);
        }
        return "redirect:board/view?id="+postId;
    }
    @GetMapping("view/update")
    public String postUpdate(@RequestParam("postId") String postId, Model model){
        long id = Long.parseLong(postId);
        Post post = postService.findPost(id);
        PostDto postDto = PostDto.of(post);
        model.addAttribute("postDto", postDto);
        return "board/PostUpdate";
    }
    @GetMapping("view/delete")
    public String postDelete(@RequestParam("postId") String postId){
        long id = Long.parseLong(postId);
        postService.deletePost(id);
        return "redirect:/";
    }

    @GetMapping("/cGood/{id}")
    public String cGood(@PathVariable("id") String commentId,HttpServletRequest request, HttpServletResponse response){
        long id = Long.parseLong(commentId);
        Cookie[] cookies = request.getCookies();
        Comment comment = commentService.findCommentId(id).get();
        int goodCount = 1;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cGood".equals(cookie.getName())) {
                    goodCount += Integer.parseInt(cookie.getValue());
                    break;
                }
            }
        }
        if( goodCount <= 10) {
            Cookie cookie = new Cookie("cGood", Integer.toString(goodCount));
            cookie.setMaxAge(3600); // 쿠키의 유효 시간 설정 (초 단위, 여기서는 1시간)
            cookie.setPath("/"); // 쿠키의 경로 설정 (모든 경로에 쿠키를 사용할 수 있도록 설정)
            commentService.good(id);
            // 응답에 쿠키 추가
            response.addCookie(cookie);
        }
        return "redirect:board/view?id="+comment.getPost().getId();
    }
    //싫어요
    @GetMapping("/cBad/{id}")
    public String cBad(@PathVariable("id") String postId,HttpServletRequest request, HttpServletResponse response){
        long id = Long.parseLong(postId);
        Cookie[] cookies = request.getCookies();
        Comment comment = commentService.findCommentId(id).get();
        int goodCount = 1;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cBad".equals(cookie.getName())) {
                    goodCount += Integer.parseInt(cookie.getValue());
                    break;
                }
            }
        }
        if( goodCount <= 10) {
            Cookie cookie = new Cookie("cBad", Integer.toString(goodCount));
            cookie.setMaxAge(3600); // 쿠키의 유효 시간 설정 (초 단위, 여기서는 1시간)
            cookie.setPath("/"); // 쿠키의 경로 설정 (모든 경로에 쿠키를 사용할 수 있도록 설정)
            commentService.bad(id);
            // 응답에 쿠키 추가
            response.addCookie(cookie);
        }
        return "redirect:board/view?id="+comment.getPost().getId();
    }
    @GetMapping("/cDelete/{id}")
    public String commentDelete(@PathVariable("id") String postId){
        long id = Long.parseLong(postId);
        long post_id = commentService.deleteComment(id);
        return "redirect:board/view?id="+post_id;
    }
}
