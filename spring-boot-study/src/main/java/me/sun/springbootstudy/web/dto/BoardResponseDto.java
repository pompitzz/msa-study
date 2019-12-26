package me.sun.springbootstudy.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.domain.board.Board;

@NoArgsConstructor
@Getter
public class BoardResponseDto {

    Long id;
    String title;
    String content;
    String author;

    public BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
