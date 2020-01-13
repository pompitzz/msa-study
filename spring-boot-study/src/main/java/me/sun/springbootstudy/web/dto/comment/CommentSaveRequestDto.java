package me.sun.springbootstudy.web.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class CommentSaveRequestDto {

    @NotNull
    private Long boardId;

    private Long parentId = -1L;
    @NotNull
    private String content;

    @Builder
    public CommentSaveRequestDto(Long boardId, Long parentId, String content) {
        this.boardId = boardId;
        this.parentId = parentId;
        this.content = content;

    }
}
