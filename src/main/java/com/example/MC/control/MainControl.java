package com.example.MC.control;

import com.example.MC.dto.PostDto;
import com.example.MC.entity.Post;
import com.example.MC.service.PostService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MainControl {

    private final PostService postService;
    @GetMapping("/")
    public String main(Model model){
        List<PostDto> hitList = postService.getHitList();
        List<PostDto> viewList = postService.getViewList();
        List<PostDto> commentList = postService.getCommentList();
        List<PostDto> newsList = postService.getNewsList();

        model.addAttribute("hit", hitList);
        model.addAttribute("view", viewList);
        model.addAttribute("comment", commentList);
        model.addAttribute("news", newsList);
        return "main";
    }
}
