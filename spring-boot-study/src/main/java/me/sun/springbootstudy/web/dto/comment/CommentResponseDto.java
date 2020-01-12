package me.sun.springbootstudy.web.dto.comment;

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
    private String name;

    private Long parentId;
    private Boolean isMore;
    private List<CommentResponseDto> childrenResponseDto;

    public CommentResponseDto(Long commentId, String content, LocalDateTime createDate, Integer depth, Long parentId, String name) {
        this.commentId = commentId;
        this.content = content;
        this.createDate = createDate;
        this.depth = depth;
        this.parentId = parentId;
        this.name = name;
    }

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createDate = comment.getCreateDate();
        this.depth = comment.getDepth();
        this.name = comment.getMember().getName();
    }
}
