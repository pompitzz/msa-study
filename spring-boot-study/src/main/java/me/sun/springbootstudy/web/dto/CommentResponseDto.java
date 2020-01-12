package me.sun.springbootstudy.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.sun.springbootstudy.domain.comment.Comment;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CommentResponseDto {

    private Long commentId;
    private String content;
    private LocalDateTime createDate;
    private Integer depth;
    private List<CommentResponseDto> commentResponseDtos;
    private Long parentId;
    private Boolean isMore;

    public CommentResponseDto(Long commentId, String content, LocalDateTime createDate, Integer depth, Long parentId) {
        this.commentId = commentId;
        this.content = content;
        this.createDate = createDate;
        this.depth = depth;
        this.parentId = parentId;
    }

    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.commentId = comment.getId();
        this.createDate = comment.getCreateDate();
        this.depth = comment.getDepth();
    }
}
