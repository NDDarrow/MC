package com.example.MC.control;

import com.example.MC.dto.CommentDto;
import com.example.MC.dto.FollowerDto;
import com.example.MC.dto.MemberDto;
import com.example.MC.dto.PostDto;
import com.example.MC.entity.Comment;
import com.example.MC.entity.Follower;
import com.example.MC.entity.Member;
import com.example.MC.entity.Post;
import com.example.MC.repository.CommentRepo;
import com.example.MC.repository.PostRepo;
import com.example.MC.service.CommentService;
import com.example.MC.service.MemberService;
import com.example.MC.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

import static com.example.MC.dto.MemberDto.createMemberDto;
import static org.aspectj.bridge.MessageUtil.error;


@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberControl {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final PostRepo postRepo;
    private  final PostService postService;
    private final CommentService commentService;
    private final CommentRepo commentRepo;

    //팔로우
    @GetMapping("/Follow")
    String addFollower(@RequestParam("postId") String mId, Principal principal, HttpServletRequest request, Model model){
        long id = Long.parseLong(mId);
        Post post = postRepo.findById(id).get();
        Member follower = memberService.findByEmail(principal.getName());
        Member follow = memberService.findById(post.getMember().getId()).get();
        memberService.makeFollowShip(follower, follow);
        String referer = request.getHeader("Referer");
        model.addAttribute("followSuccess","팔로우가 되었습니다");
        return "redirect:" + referer;
    }
    @GetMapping("/cFollow")
    String addCFollower(@RequestParam("commentId") String mId, Principal principal, HttpServletRequest request, Model model){
        long id = Long.parseLong(mId);
        Comment comment = commentRepo.findById(id).get();
        Member follower = memberService.findByEmail(principal.getName());
        Member follow = memberService.findCommentWriter(id);
        memberService.makeFollowShip(follower, follow);
        String referer = request.getHeader("Referer");
        model.addAttribute("followSuccess","팔로우가 되었습니다");
        return "redirect:" + referer;
    }

    //회원가입 이동 완
    @GetMapping("/signUp")
    public String memberForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/signUp";
    }
    //회원가입 요청 완
    @PostMapping("/signUp")
    public String newMember(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "member/signUp";
        }
        try {
            memberService.saveMember(memberDto, passwordEncoder);
        } catch (IllegalStateException e) {//memberService에서 보낸 에러메세지 캐치
            model.addAttribute("errorMessage", e.getMessage());
            return "member/signUp";
        }
        return "redirect:/members/login";
    }


    //로그인 완
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }
    //로그인 에러 몰?루
    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginError", "아이디 혹은 비밀번호 오류");
        return "member/login";
    }

    //아이디 찾기 요청 완
    @PostMapping("/IdFind")
    public String foundId(@RequestParam String name, @RequestParam String tell, Model model) {
        Member user = memberService.findEmail(name, tell);
        if (user != null) {
            model.addAttribute("email", user.getEmail());
            return "member/idFind";
        }
        return "member/idFind";
    }
    //아이디 찾기 이동 완
    @GetMapping("/IdFind")
    public String foundIdForm() {
        return "member/idFind";
    }

    //비밀번호 찾기 이동 완
    @GetMapping("/PwFind")
    public String foundPwForm(Model model) {

        return "member/pwFind";
    }
    //비밀번호 찾기 요청 완
    @PostMapping("/PwFind")
    public String foundPw(@RequestParam String email, @RequestParam String tell, Model model) {
        Member user = memberService.findPw(email, tell);
        if (user != null) {
            model.addAttribute("pwFindMessage", "비밀번호를 재설정해 주십시오");
            model.addAttribute("email", email);
            model.addAttribute("tell", tell);
            return "member/pwFind";
        } else {
            model.addAttribute("NonId", "이메일이 잘못되었거나 없는 이메일입니다.");
            return "member/pwFind";
        }
    }
    //비밀번호 재설정 요청 완
    @PostMapping("/ResetPw")
    public String resetPw(@RequestParam String user_email, String user_tell, String password, Model model) {

        memberService.resetPW(user_email, user_tell, password, passwordEncoder);
        model.addAttribute("resetPw", "비밀번호가 재설정 되었습니다.");
        return "redirect:/";
    }

    //내정보 이동
    @GetMapping(value = {"/MyPage","/MyPage/{page}"})
    public String myPage(Principal principal,@RequestParam("tag") String tag, @PathVariable("page") Optional<Integer> page, Model model){
        String userEmail = principal.getName();
        Member user = memberService.findByEmail(userEmail);
        if(tag.equals("post")) {
            Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
            Page<PostDto> myList = postService.getMyList(user, pageable);
            model.addAttribute("items", myList);
            model.addAttribute("maxPage",5);
        }else if(tag.equals("comment")){
            Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
            Page<CommentDto> myList = commentService.getMyList(user, pageable);
            model.addAttribute("item", myList);
            model.addAttribute("maxPage",5);
        }else if(tag.equals("follow")){
            Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
            Page<FollowerDto> myList= memberService.getMyList(user, pageable);
            model.addAttribute("follow", myList);
            model.addAttribute("maxPage",5);
        }
        int myPostCnt = postService.myPostCnt(user);
        int myCommentCnt = commentService.myCommentCnt(user);

        MemberDto userDto = createMemberDto(user);
        model.addAttribute("postCnt", myPostCnt);
        model.addAttribute("commentCnt",myCommentCnt);
        model.addAttribute("user",userDto);
        return "member/myPage";
    }
    @GetMapping(value = {"/FPage","/FPage/{page}"})
    public String openPage(@RequestParam("followerId") String id,@PathVariable("page") Optional<Integer> page, Model model){
        long followerId = Long.parseLong(id);
        Member follower = memberService.findById(followerId).get();
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<PostDto> FList = postService.getMyList(follower, pageable);
        model.addAttribute("items",FList);

        int myPostCnt = postService.myPostCnt(follower);
        int myCommentCnt = commentService.myCommentCnt(follower);

        MemberDto userDto = createMemberDto(follower);
        model.addAttribute("postCnt", myPostCnt);
        model.addAttribute("commentCnt",myCommentCnt);
        model.addAttribute("user",userDto);
        return "member/FPage";

    }
    //내 정보 수정 이동
    @GetMapping("/MyUpdate")
    public String updateForm( Principal principal, Model model){
        String userEmail = principal.getName();
        Member user = memberService.findByEmail(userEmail);
        MemberDto userDto = createMemberDto(user);
        model.addAttribute("memberDto",userDto);
        return "member/myUpdate";
    }
    //내정보 수정
    @PostMapping("/MyUpdate")
    public String updateInfo(@Valid MemberDto memberDto,BindingResult bindingResult, Model model , Principal principal){
        System.out.println("in");
        if(bindingResult.hasErrors()){
            return "member/myUpdate";
        }
        try {
            memberService.updateMember(memberDto);
        } catch (IllegalStateException e) {//memberService에서 보낸 에러메세지 캐치
            model.addAttribute("errorMessage", e.getMessage());
            return "member/signUp";
        }
        return "redirect:/members/MyPage";
    }
    //회원탈퇴 이동
    @GetMapping("/ReSign")
    public String ReSignForm(Model model){
        return "member/reSign";
    }
    //회원 탈퇴
    @PostMapping("/ReSign")
    public String ReSign(String email, String pw, Principal principal, Model model){
        if(principal.getName().equals(email)) {
            memberService.reSign(email);
            return "/";
        }else{
            model.addAttribute("disaccordEmail", "유저정보가 다릅니다");
            return "redirect:/";
        }
    }
}
