package me.sun.springbootstudy.web.dto.board;

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
    String email;
    LocalDateTime lastModifiedDate;
    BoardType boardType;
//    Slice<CommentResponseDto> commentList;


    public BoardOneResponseDto(Board entity) {
        this.title = entity.getTitle();
        this.author = entity.getMember().getName();
        this.boardType = entity.getBoardType();
        this.content = entity.getContent();
        this.lastModifiedDate = entity.getLastModifiedDate();
        this.email = entity.getMember().getEmail();

//        List<Comment> comments = entity.getComments();

    }
}
