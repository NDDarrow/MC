package com.example.MC.control;

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
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PostControl {
    private final PostService postService;
    private final MemberService memberService;

    @GetMapping
    public String OpenPost(Model model){
        return "/";
    }
    @PostMapping
    public String writePost(@Valid PostDto postDto, BindingResult bindingResult, Principal principal){
        Member user =memberService.findByEmail(principal.getName());
        Post post = Post.createPost(postDto, user);
        postService.writePost(post);
        return "redirect:/";
    }
    @PostMapping("/Aboard/post")
    public String writeAboard(@Valid PostDto postDto, BindingResult bindingResult){
        return null;
    }
}
