package com.example.MC.dto;

import com.example.MC.entity.PostImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;

@Getter @Setter
public class PostImgDto {
    private Long id;
    private String imgName;
    private String imgUrl;
    private String oriImgName;

    private static ModelMapper mapper = new ModelMapper();

    public static PostImgDto of(PostImg postImg){
        return mapper.map(postImg, PostImgDto.class);
    }
}
