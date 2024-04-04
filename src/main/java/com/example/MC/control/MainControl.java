package com.example.MC.control;

import com.example.MC.service.PostService;
import com.example.MC.vo.test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class MainControl {
    private final PostService postService;

    @GetMapping("/")
    public String main(){

        System.out.println("sex");
        return "main";
    }

    @GetMapping("/post/{sex}")
    public String PostManTest(@PathVariable String sex){
        System.out.println(sex);
        return sex;
    }
}
