package me.sun.springbootstudy.web.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.domain.board.Board;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class BoardSaveRequestDto {

    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private String author;

    @Builder
    public BoardSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
