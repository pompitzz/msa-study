package me.sun.springbootstudy.web.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.domain.board.Board;
import me.sun.springbootstudy.domain.board.BoardType;

@NoArgsConstructor
@Getter
public class BoardOneResponseDto {

    Long id;
    String title;
    String content;
    BoardType boardType;


    public BoardOneResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.boardType = entity.getBoardType();
        this.content = entity.getContent();
    }
}
