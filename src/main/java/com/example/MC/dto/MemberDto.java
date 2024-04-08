package com.example.MC.dto;

import com.example.MC.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberDto {

    @NotEmpty( message = "이메일 필수 입력")
    @Email( message = "잘못된 형식")
    private String email;
    @NotEmpty( message = "비민번호 필수 입력")
    @Length(min=6,max=16, message = "6~12자")
    private String password;

    private String userNick;

    private String name;

    private String tell;

    private String zipCode;

    private String addr1;

    private String addr2;

    public static MemberDto createMemberDto(Member member){
        MemberDto memberDto = new MemberDto();
        memberDto.setName(member.getName());
        memberDto.setUserNick(member.getUserNick());
        memberDto.setTell(member.getTell());
        memberDto.setEmail(member.getEmail());
        memberDto.setZipCode(member.getZipCode());
        memberDto.setAddr1(member.getAddr1());
        memberDto.setAddr2(member.getAddr2());
        return memberDto;
    }
}
