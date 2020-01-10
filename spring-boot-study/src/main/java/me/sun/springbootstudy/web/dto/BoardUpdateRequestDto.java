package me.sun.springbootstudy.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.domain.board.BoardType;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class BoardUpdateRequestDto {

    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private BoardType boardType;

    @Builder
    public BoardUpdateRequestDto(String title, String content, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
    }
}
