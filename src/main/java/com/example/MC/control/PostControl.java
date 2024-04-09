package com.example.MC.control;

import com.example.MC.dto.APostDto;
import com.example.MC.dto.PostDto;
import com.example.MC.entity.Member;
import com.example.MC.entity.Post;
import com.example.MC.service.MemberService;
import com.example.MC.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostControl {
    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/{form}")
    public String Openform(@PathVariable("form")String form, Model model){

        return "/board/{form}";
    }
    @PostMapping()
    public String writePost(@Valid PostDto postDto, BindingResult bindingResult, Principal principal){
        Member user =memberService.findByEmail(principal.getName());
        Post post = Post.createPost(postDto, user);
        postService.writePost(post);
        return "redirect:/";
    }
    @PostMapping("/Aboard/post")
    public String writeAboard(@Valid APostDto aPostDto, BindingResult bindingResult){
        return null;
    }
}
