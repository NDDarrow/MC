package com.example.MC.service;

import com.example.MC.dto.PostImgDto;
import com.example.MC.entity.PostImg;
import com.example.MC.repository.PostImgRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class PostImgService {
    @Value("${itemImgLocation}")
    private String imgLocation;

    private final  FileService fileService;
    private final PostImgRepo postImgRepo;

    public void savePostImg(PostImg postImg, MultipartFile multipartFile) throws Exception{
        String oriName = multipartFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if( !StringUtils.isEmpty(oriName)){ //사용자가 업로드 한 원본 이미지이름 여부
            imgName = fileService.uploadFile(imgLocation, oriName, multipartFile.getBytes());
            imgUrl = "/images/post/" + imgName;
        }
        postImg.setImgUrl(imgUrl);
        postImg.setImgName(imgName);
        postImg.setOriImgName(oriName);
        //데이터베이스 저장은 Entity로 한다.
        postImgRepo.save(postImg);
    }
}
