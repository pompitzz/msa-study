package me.sun.springbootstudy.web.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentSaveRequestDto {

    private Long boardId;
    private Long parentId;
    private String content;
    private String email;

    @Builder
    public CommentSaveRequestDto(Long boardId, Long parentId, String content, String email) {
        this.boardId = boardId;
        this.parentId = parentId;
        this.content = content;
        this.email = email;

    }
}
