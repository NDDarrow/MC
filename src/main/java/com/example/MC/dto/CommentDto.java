package com.example.MC.dto;

import com.example.MC.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Comparator;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String writer;
    @NotNull(message = "내용이 있어야 합니다.")
    private String body;
    private LocalDateTime regTime;
    private int good;
    private int bad;
    private String createdBy;
    private Long cId=0L;

    public static CommentDto createDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setBody(comment.getBody());
        commentDto.setGood(comment.getGood());
        commentDto.setBad(comment.getBad());
        commentDto.setRegTime(comment.getRegTime());
        commentDto.setCreatedBy(comment.getCreatedBy());
        commentDto.setId(comment.getId());
        if(comment.getCId() !=null){
            commentDto.setCId(comment.getCId());
        }
        return commentDto;
    }
    public static Comparator<CommentDto> commentComparator = new Comparator<CommentDto>() {
        @Override
        public int compare(CommentDto c1, CommentDto c2) {
            if (c1.getCId() != null && c1.getCId().equals(c2.getId())) {
                return 1; // c1이 대댓글일 경우 c2보다 뒤에 위치하도록 합니다.
            } else if (c2.getCId() != null && c2.getCId().equals(c1.getId())) {
                return -1; // c2가 대댓글일 경우 c1보다 앞에 위치하도록 합니다.
            } else if (c1.getCId() != null && c2.getCId() != null && c1.getId().equals(c2.getCId())) {
                return -1; // c1이 댓글이고 c2가 대댓글이면 c1이 먼저 나오도록 합니다.
            } else if (c1.getCId() < c2.getId()){
                return 1;
            } else  if(c1.getCId() == c2.getCId() && c1.getId() < c2.getId()) {
                return 1;
            }else {
                return 0; // 그 외의 경우에는 순서를 변경하지 않습니다.
            }
        }
    };

}

