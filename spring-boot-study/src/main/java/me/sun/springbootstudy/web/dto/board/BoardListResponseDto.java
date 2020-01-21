package me.sun.springbootstudy.web.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.domain.board.Board;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class BoardListResponseDto {

    Long id;
    String title;
    String author;
    LocalDateTime createDate;

    int viewsCount;
    int commentCount;

    public BoardListResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getMember().getName();
        this.createDate = entity.getCreateDate();
        this.viewsCount = entity.getViewsCount();
        this.commentCount = entity.getCommentCount();
    }
}
