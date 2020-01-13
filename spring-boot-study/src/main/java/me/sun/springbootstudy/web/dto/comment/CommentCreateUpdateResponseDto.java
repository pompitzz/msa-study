package me.sun.springbootstudy.web.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.domain.comment.Comment;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CommentCreateUpdateResponseDto {

    private Long commentId;
    private String content;
    private LocalDateTime createDate;
    private Integer depth;
    private String name;
    private Long parentId;

    public CommentCreateUpdateResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createDate = comment.getCreateDate();
        this.depth = comment.getDepth();
        this.name = comment.getMember().getName();

        if (comment.getParent() != null) {
            this.parentId = comment.getParent().getId();
        }
    }
}
