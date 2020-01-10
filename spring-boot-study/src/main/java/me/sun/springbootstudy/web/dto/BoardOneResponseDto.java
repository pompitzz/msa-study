package me.sun.springbootstudy.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.domain.board.Board;
import me.sun.springbootstudy.domain.board.BoardType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class BoardOneResponseDto {

    String title;
    String content;
    String author;
    LocalDateTime lastModifiedDate;
    BoardType boardType;


    public BoardOneResponseDto(Board entity) {
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.boardType = entity.getBoardType();
        this.content = entity.getContent();
        this.lastModifiedDate = entity.getLastModifiedDate();
    }
}
