package com.example.MC.control;

import com.example.MC.dto.MemberDto;
import com.example.MC.entity.Member;
import com.example.MC.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberControl {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signUp")
    public String memberForm(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "member/SignUp";
    }
    @PostMapping("/signUp")
    public String newMember(@Valid MemberDto memberDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "member/SignUp";
        }
        try{
            memberService.saveMember(memberDto, passwordEncoder);
        }catch(IllegalStateException e){//memberService에서 보낸 에러메세지 캐치
            model.addAttribute("errorMessage", e.getMessage());
            return "member/SignUp";
        }
        return "redirect:/";
    };

    @GetMapping("/login")
    public String login(){
        return "member/Login";
    }
    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginError","아이디 혹은 비밀번호 오류");
        return "member/Login";
    }

    @PostMapping("/IdFind")
    public String foundId(String name, String tell){
        Member user = memberService.findEmail(name, tell);
        if(user != null){

        }
        return "member/IdFind";
    }
    @GetMapping("/IdFind")
    public String foundIdForm(Model model){

        return "member/IdFind";
    }

    @GetMapping("/PwFind")
    public String foundPwForm(Model model){
        return null;
    }
    @PostMapping("/PwFind")
    public Member foundPw(String email, String tell){
        Member user = memberService.findPw(email, tell);
        if(user != null) return user;
        else return null;
    }

    @GetMapping("/ReSign")
    public String ReSignForm(Model model){
        return "member/reSign";
    }
    @PostMapping("/ReSign")
    public String ReSign(String email, String pw){
        memberService.reSign(email, pw);
        return "redirect:/";
    }

    @GetMapping("/MyPage")
    public String myPage(Model model){
        return "member/myPage";
    }
    @GetMapping("/MyUpdate")
    public String updateForm(Model model){
        return "member/myUpdate";
    }
    @PostMapping("/MyUpdate")
    public String updateStat(Model model){
        return "redirect:/";
    }
}
