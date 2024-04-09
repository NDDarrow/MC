package com.example.MC.control;

import com.example.MC.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MainControl {
    private final PostService postService;

    @GetMapping("/")
    public String main(Model model){

        return "main";
    }
}
