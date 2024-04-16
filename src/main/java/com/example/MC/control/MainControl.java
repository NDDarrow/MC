package com.example.MC.control;

import com.example.MC.dto.ItemSearchDto;
import com.example.MC.dto.PostDto;
import com.example.MC.entity.Post;
import com.example.MC.service.PostService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MainControl {

    private final PostService postService;
//    메인페이지 이동
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
    @GetMapping(value = {"/search", "search/{page}"})
    public String search(@PathVariable("page") Optional<Integer> page, @RequestParam("query")String query, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<PostDto> postDtoPage = postService.searchPost(query,pageable);
        model.addAttribute("searchResult",postDtoPage);
        model.addAttribute("itemSearchDto", new ItemSearchDto() );
        return "/board/search";
    }
}
