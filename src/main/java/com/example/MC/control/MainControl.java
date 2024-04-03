package com.example.MC.control;

import com.example.MC.service.Postservice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MainControl {
    private final Postservice postservice;

    @GetMapping("/")
    public String main(){
        return "main";
    }
}
